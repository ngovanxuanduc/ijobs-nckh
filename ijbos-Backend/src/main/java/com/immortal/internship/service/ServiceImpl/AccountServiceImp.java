package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.constant.AccountActiveConstants;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.entity.*;
import com.immortal.internship.exception.BaseException;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.payload.request.*;
import com.immortal.internship.payload.response.CompanyProfileResponse;
import com.immortal.internship.payload.response.PageResponse;
import com.immortal.internship.payload.response.StudentProfileResponse;
import com.immortal.internship.payload.response.TeacherProfileResponse;
import com.immortal.internship.repository.CustomRepository;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.AccountService;
import com.immortal.internship.service.AmazonClient;
import com.immortal.internship.service.UserPrinciple;
import com.immortal.internship.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private DBContext dbContext;

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    UserProvider userProvider;

    @Autowired
    @Qualifier("ExceptionAccountNotFound")
    InvalidParamException exAccountNotFound;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<List<AccountEntity>> getAllAccount() {
        return Optional.ofNullable(dbContext.accountRepository
                .findAllByStatusGreaterThan(AccountActiveConstants.DOES_NOT_EXIST))
                .orElseThrow(()-> exAccountNotFound);
    }

    @Override
    public AccountEntity createAccount(BaseAccountForm infAcc) {
        checkExistedUsername(infAcc.getUsername());
        validParamRequest(UserAccountValidationUtil.isValidateUserName(infAcc.getUsername())
                , MessageConstants.ForUser.INVALID_NAME);
        validParamRequest(UserAccountValidationUtil.isValidatePassword(infAcc.getPassword())
                , MessageConstants.ForUser.INVALID_PASSWORD);
        UserPrinciple currentAccount = userProvider.getCurrentUser();
        String logoImgURL = saveImageToS3(infAcc.getImageLogoURL());
        String backgroundImgURL = saveImageToS3(infAcc.getImageBackgroundURL());
        AccountEntity accountEntity = AccountEntity.builder()
                .userName(infAcc.getUsername())
                .password(passwordEncoder.encode(infAcc.getPassword()))
                .status(AccountActiveConstants.UNFINISHED_PROFILE)
                .createBy(currentAccount.getAccount().getUserName())
                .updatedBy(null)
                .imageEntity(null)
                .roles(getRoleFromRoleList(infAcc.getRoles()))
                .build();
        dbContext.accountRepository.save(accountEntity);
        ImageEntity imageEntity = ImageEntity.builder()
                .id(accountEntity.getId())
                .logo(logoImgURL)
                .background(backgroundImgURL)
                .build();
        accountEntity.setImageEntity(imageEntity);
        return dbContext.accountRepository.save(accountEntity);
    }
    @Override
    public StudentEntity createStudentAccount(StudentAccountFormCreate infStAcc) {
        List<Integer> rolesList = Arrays.asList(RoleConstants.ADMIN
                ,RoleConstants.COMPANY,RoleConstants.TEACHER);
        validParamRequest(!rolesList.stream().anyMatch(x->infStAcc.getRoles().contains(x))
                ,MessageConstants.ForUser.STUDENT_OUT_OF_ROLE);
        AccountEntity accountEntity = createAccount(infStAcc);
        StudentEntity studentEntity = StudentEntity.builder()
                .id(accountEntity.getId())
                .studentCode(infStAcc.getStudentCode())
                .schoolYear(infStAcc.getSchoolYear())
                .firstName(infStAcc.getFirstName())
                .lastName(infStAcc.getLastName())
                .birthDate(infStAcc.getBirthDate())
                .gender(infStAcc.isGender())
                .email(infStAcc.getEmail())
                .phoneNumber(infStAcc.getPhoneNumber())
                .address(infStAcc.getAddress())
                .department(infStAcc.getDepartment())
                .klass(infStAcc.getKlass())
                .build();
        accountEntity.setStatus(AccountActiveConstants.NORMAL_ACCOUNT);
        StudentEntity res = null;
        try{
            res = dbContext.studentRepository.save(studentEntity);
        }catch (DataIntegrityViolationException ex){
            accountEntity.getRoles().clear();
            accountEntity.setImageEntity(null);
            dbContext.accountRepository.save(accountEntity);
            dbContext.accountRepository.delete(accountEntity);
        }
        return Optional.ofNullable(res)
                .orElseThrow(() ->new BaseException(MessageConstants.ForSystem.INVALID_PARAM
                        ,MessageConstants.ForUser.EXISTED_STUDENT_CODE
                        ,MessageConstants.ResultCode.BAD_REQUEST_PARAMS));
    }

    @Override
    public TeacherEntity createLectureAccount(SchoolAccountFormCreate infSchAcc) {
        List<Integer> rolesList = Arrays.asList(RoleConstants.COMPANY,RoleConstants.STUDENT,RoleConstants.ADMIN);
        validParamRequest(!rolesList.stream().anyMatch(x->infSchAcc.getRoles().contains(x))
                ,MessageConstants.ForUser.TEACHER_OUT_OF_ROLE);
        AccountEntity accountEntity = createAccount(infSchAcc);
        TeacherEntity teacherEntity = TeacherEntity.builder()
                .id(accountEntity.getId())
                .address(infSchAcc.getAddress())
                .department(infSchAcc.getDepartment())
                .email(infSchAcc.getEmail())
                .firstName(infSchAcc.getFirstName())
                .lastName(infSchAcc.getLastName())
                .gender(infSchAcc.isGender())
                .phoneNumber(infSchAcc.getPhoneNumber())
                .build();
        accountEntity.setStatus(AccountActiveConstants.NORMAL_ACCOUNT);
        dbContext.accountRepository.save(accountEntity);
        return dbContext.teacherRepository.save(teacherEntity);
    }


    @Override
    public TeacherEntity createAdminAccount(SchoolAccountFormCreate infSchAcc) {
        List<Integer> rolesList = Arrays.asList(RoleConstants.COMPANY,RoleConstants.STUDENT,RoleConstants.TEACHER);
        validParamRequest(!rolesList.stream().anyMatch(x->infSchAcc.getRoles().contains(x))
                ,MessageConstants.ForUser.ADMIN_OUT_OF_ROLE);
        AccountEntity accountEntity = createAccount(infSchAcc);
        TeacherEntity teacherEntity = TeacherEntity.builder()
                .id(accountEntity.getId())
                .address(infSchAcc.getAddress())
                .department(infSchAcc.getDepartment())
                .email(infSchAcc.getEmail())
                .firstName(infSchAcc.getFirstName())
                .lastName(infSchAcc.getLastName())
                .gender(infSchAcc.isGender())
                .phoneNumber(infSchAcc.getPhoneNumber())
                .build();
        accountEntity.setStatus(AccountActiveConstants.NORMAL_ACCOUNT);
        dbContext.accountRepository.save(accountEntity);
        return dbContext.teacherRepository.save(teacherEntity);
    }

    @Override
    public CompanyEntity createCompanyAccount(CompanyAccountFormCreate infComAcc) {
        List<Integer> rolesList = Arrays.asList(RoleConstants.STUDENT
                ,RoleConstants.TEACHER,RoleConstants.ADMIN);
        validParamRequest(!rolesList.stream().anyMatch(x->infComAcc.getRoles().contains(x))
                ,MessageConstants.ForUser.COMPANY_OUT_OF_ROLE);
        AccountEntity accountEntity = createAccount(infComAcc);
        CompanyEntity companyEntity = CompanyEntity.builder()
                .id(accountEntity.getId())
                .email(infComAcc.getEmail())
                .companyInfo(infComAcc.getCompanyInfo())
                .country(infComAcc.getCountry())
                .location(infComAcc.getLocation())
                .name(infComAcc.getName())
                .overView(infComAcc.getOverView())
                .phoneNumber(infComAcc.getPhoneNumber())
                .workingDate(infComAcc.getWorkingDate())
                .build();
        accountEntity.setStatus(AccountActiveConstants.NORMAL_ACCOUNT);
        dbContext.accountRepository.save(accountEntity);
        return dbContext.companyRepository.save(companyEntity);
    }


    @Override
    // co the get ca nhung account da bi danh dau xoa
    public List<AccountEntity> getAccountSortByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort) {
        checkValidPage(pageNumber,pageSize,sortBy,typeSort);
        validParamRequest(AccountValidationUtil.isValidatedAccountSortBy(sortBy), MessageConstants.ForUser.SORT_BY_ERROR);
        Sort sort = Sort.by(sortBy);
        sort = typeSort.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return dbContext.accountRepository.findAllByStatusGreaterThan(AccountActiveConstants.DOES_NOT_EXIST).get();
    }

    @Override
    public String updateStudentAccount(StudentProfileUpdate infAcc, UUID id) {
        UserPrinciple currentAccount = userProvider.getCurrentUser();
        AccountEntity accountEntity = dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST);
        checkValidAccount(accountEntity, RoleConstants.STUDENT);
        StudentEntity studentEntity = accountEntity.getStudent();
        studentEntity.setAddress(infAcc.getAddress());
        studentEntity.setBirthDate(infAcc.getBirthDate());
        studentEntity.setDepartment(infAcc.getDepartment());
        studentEntity.setEmail(infAcc.getEmail());
        studentEntity.setFirstName(infAcc.getFirstName());
        studentEntity.setLastName(infAcc.getLastName());
        studentEntity.setStudentCode(infAcc.getStudentCode());
        studentEntity.setGender(infAcc.isGender());
        studentEntity.setKlass(infAcc.getKlass());
        studentEntity.setPhoneNumber(infAcc.getPhoneNumber());
        studentEntity.setSchoolYear(infAcc.getSchoolYear());
        String logoImgURL = saveImageToS3(infAcc.getLogo());
        String backgroundImgURL = saveImageToS3(infAcc.getBackground());
        ImageEntity imageEntity = accountEntity.getImageEntity();
        imageEntity.setLogo(logoImgURL);
        imageEntity.setBackground(backgroundImgURL);
        accountEntity.setUpdatedBy(currentAccount.getAccount().getUserName());
        dbContext.accountRepository.save(accountEntity);
        return MessageConstants.ForUser.UPDATE_ACCOUNT_SUCCESS;
    }

    @Override
    public String updateTeacherAccount(TeacherProfileUpdate infAcc, UUID id) {
        UserPrinciple currentAccount = userProvider.getCurrentUser();
        System.out.println(currentAccount.getAccount().getId());
        AccountEntity accountEntity = dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST);
        checkValidAccount(accountEntity, RoleConstants.TEACHER);
        TeacherEntity teacherEntity = accountEntity.getTeacher();
        teacherEntity.setPhoneNumber(infAcc.getPhoneNumber());
        teacherEntity.setGender(infAcc.isGender());
        teacherEntity.setEmail(infAcc.getEmail());
        teacherEntity.setDepartment(infAcc.getDepartment());
        teacherEntity.setAddress(infAcc.getAddress());
        teacherEntity.setFirstName(infAcc.getFirstName());
        teacherEntity.setLastName(infAcc.getLastName());
        String logoImgURL = saveImageToS3(infAcc.getLogo());
        String backgroundImgURL = saveImageToS3(infAcc.getBackground());
        ImageEntity imageEntity = accountEntity.getImageEntity();
        imageEntity.setLogo(logoImgURL);
        imageEntity.setBackground(backgroundImgURL);
        accountEntity.setUpdatedBy(currentAccount.getAccount().getUserName());
        dbContext.accountRepository.save(accountEntity);
        return MessageConstants.ForUser.UPDATE_ACCOUNT_SUCCESS;
    }

    @Override
    public String updateCompanyAccount(CompanyProfileUpdate infAcc, UUID id) {
        UserPrinciple currentAccount = userProvider.getCurrentUser();
        AccountEntity accountEntity = dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST);
        checkValidAccount(accountEntity, RoleConstants.COMPANY);
        CompanyEntity companyEntity = accountEntity.getCompany();
        companyEntity.setCompanyInfo(infAcc.getCompanyInfo());
        companyEntity.setCountry(infAcc.getCountry());
        companyEntity.setEmail(infAcc.getEmail());
        companyEntity.setLocation(infAcc.getLocation());
        companyEntity.setName(infAcc.getName());
        companyEntity.setOverView(infAcc.getOverView());
        companyEntity.setWorkingDate(infAcc.getWorkingDate());
        String logoImgURL = saveImageToS3(infAcc.getLogo());
        String backgroundImgURL = saveImageToS3(infAcc.getBackground());
        ImageEntity imageEntity = accountEntity.getImageEntity();
        imageEntity.setLogo(logoImgURL);
        imageEntity.setBackground(backgroundImgURL);
        accountEntity.setUpdatedBy(currentAccount.getAccount().getUserName());
        dbContext.accountRepository.save(accountEntity);
        return MessageConstants.ForUser.UPDATE_ACCOUNT_SUCCESS;
    }

    @Override
    public String deleteAccount(UUID id) {
        AccountEntity accInDB = dbContext.accountRepository.findById(id)
                .orElseThrow(() -> exAccountNotFound);
        accInDB.setStatus(AccountActiveConstants.DOES_NOT_EXIST);
        dbContext.accountRepository.save(accInDB);
        return MessageConstants.ForUser.DELETE_ACCOUNT_SUCCESS;
    }

    @Override
    public AccountEntity getAccountById(UUID id) {
        return Optional.ofNullable(dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST))
                .orElseThrow(()->exAccountNotFound);
    }

    @Override
    public StudentProfileResponse getStudentProfile(UUID id) {
        AccountEntity accountEntity = dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST);
        checkValidAccount(accountEntity, RoleConstants.STUDENT);
        StudentProfileResponse studentProfileResponse = StudentProfileResponse.builder()
                .accountId(accountEntity.getId())
                .address(accountEntity.getStudent().getAddress())
                .background(accountEntity.getImageEntity().getBackground())
                .roleName(getRoleEntity(accountEntity))
                .department(accountEntity.getStudent().getDepartment())
                .gender(accountEntity.getStudent().isGender())
                .address(accountEntity.getStudent().getAddress())
                .logo(accountEntity.getImageEntity().getLogo())
                .createBy(accountEntity.getCreateBy())
                .updatedBy(accountEntity.getUpdatedBy())
                .klass(accountEntity.getStudent().getKlass())
                .birthDate(accountEntity.getStudent().getBirthDate())
                .studentCode(accountEntity.getStudent().getStudentCode())
                .schoolYear(accountEntity.getStudent().getSchoolYear())
                .phoneNumber(accountEntity.getStudent().getPhoneNumber())
                .email(accountEntity.getStudent().getEmail())
                .lastName(accountEntity.getStudent().getLastName())
                .firstName(accountEntity.getStudent().getFirstName())
                .build();
        return studentProfileResponse;
    }


    @Override
    public TeacherProfileResponse getTeacherProfile(UUID id) {
        AccountEntity accountEntity = dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST);
        checkValidAccount(accountEntity, RoleConstants.TEACHER);
        TeacherProfileResponse teacherProfileResponse = TeacherProfileResponse.builder()
                .accountId(accountEntity.getId())
                .updatedBy(accountEntity.getUpdatedBy())
                .createBy(accountEntity.getCreateBy())
                .gender(accountEntity.getTeacher().getGender())
                .address(accountEntity.getTeacher().getAddress())
                .phoneNumber(accountEntity.getTeacher().getPhoneNumber())
                .department(accountEntity.getTeacher().getDepartment())
                .email(accountEntity.getTeacher().getEmail())
                .firstName(accountEntity.getTeacher().getFirstName())
                .lastName(accountEntity.getTeacher().getLastName())
                .background(accountEntity.getImageEntity().getBackground())
                .logo(accountEntity.getImageEntity().getLogo())
                .roleName(getRoleEntity(accountEntity))
                .build();

        return teacherProfileResponse;
    }

    @Override
    public CompanyProfileResponse getCompanyProfile(UUID id) {
        AccountEntity accountEntity = dbContext.accountRepository
                .findByIdAndStatusGreaterThan(id, AccountActiveConstants.DOES_NOT_EXIST);
        checkValidAccount(accountEntity, RoleConstants.COMPANY);
        CompanyProfileResponse companyProfileResponse = CompanyProfileResponse.builder()
                .accountId(accountEntity.getId())
                .updatedBy(accountEntity.getUpdatedBy())
                .createBy(accountEntity.getCreateBy())
                .overView(accountEntity.getCompany().getOverView())
                .country(accountEntity.getCompany().getCountry())
                .workingDay(accountEntity.getCompany().getWorkingDate())
                .email(accountEntity.getCompany().getEmail())
                .phoneNumber(accountEntity.getCompany().getPhoneNumber())
                .location(accountEntity.getCompany().getLocation())
                .companyInfo(accountEntity.getCompany().getCompanyInfo())
                .name(accountEntity.getCompany().getName())
                .roleName(getRoleEntity(accountEntity))
                .background(accountEntity.getImageEntity().getBackground())
                .logo(accountEntity.getImageEntity().getLogo())
                .build();
        return companyProfileResponse;
    }

    @Override
    public PageResponse getStudentProfileByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort) {
        checkValidPage(pageNumber,pageSize,sortBy,typeSort);
        validParamRequest(AccountValidationUtil.isValidatedStudentProfileSortBy(sortBy), MessageConstants.ForUser.SORT_BY_ERROR);
        Sort sort = Sort.by(sortBy);
        sort = typeSort.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        List<StudentProfileResponse> list = dbContext.customRepository.getAllStudentProfile();
        int start = Math.min((int) pageable.getOffset(), list.size());
        int end = Math.min(start+pageable.getPageSize(), list.size());
        Page<StudentProfileResponse> data = new PageImpl<StudentProfileResponse>(list.subList(start, end), pageable, list.size());
        return new PageResponse(data, data.getContent());
    }

    @Override
    public PageResponse getTeacherProfileByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort) {
        checkValidPage(pageNumber,pageSize,sortBy,typeSort);
        validParamRequest(AccountValidationUtil.isValidatedTeacherProfileSortBy(sortBy)
                , MessageConstants.ForUser.SORT_BY_ERROR);
        Sort sort = Sort.by(sortBy);
        sort = typeSort.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        List<TeacherProfileResponse> list = dbContext.customRepository.getAllTeacherProfile();
        int start = Math.min((int) pageable.getOffset(), list.size());
        int end = Math.min(start+pageable.getPageSize(), list.size());
        Page<TeacherProfileResponse> data = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return new PageResponse(data, data.getContent());
    }


    @Override
    public PageResponse getCompanyProfileByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort) {
        validParamRequest(AccountValidationUtil.isValidatedCompanyProfileSortBy(sortBy), MessageConstants.ForUser.SORT_BY_ERROR);
        checkValidPage(pageNumber,pageSize,sortBy,typeSort);
        Sort sort = Sort.by(sortBy);
        sort = typeSort.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        List<CompanyProfileResponse> list = dbContext.customRepository.getAllCompanyProfile();
        int start = Math.min((int) pageable.getOffset(), list.size());
        int end = Math.min(start+pageable.getPageSize(), list.size());
        Page<CompanyProfileResponse> data = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return new PageResponse(data, data.getContent());
    }

    private void checkValidPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort){
        validParamRequest(PageValidationUtil.isValidatedPageNumber(pageNumber), MessageConstants.ForUser.PAGE_NUMBER_ERROR);
        validParamRequest(PageValidationUtil.isValidatedPageSize(pageSize), MessageConstants.ForUser.PAGE_SIZE_ERROR);
        validParamRequest(PageValidationUtil.isValidatedSortType(typeSort), MessageConstants.ForUser.TYPE_SORT_ERROR);
    }

    private void checkValidAccount(AccountEntity accountEntity, Integer name){
        Optional.ofNullable(accountEntity).orElseThrow(()->exAccountNotFound);
        if(name==RoleConstants.COMPANY){
            Optional.ofNullable(accountEntity.getCompany()).orElseThrow(()->exAccountNotFound);
        }else if(name==RoleConstants.STUDENT){
            Optional.ofNullable(accountEntity.getStudent()).orElseThrow(()->exAccountNotFound);
        }else{
            Optional.ofNullable(accountEntity.getTeacher()).orElseThrow(()->exAccountNotFound);
        }
    }

    private String getRoleEntity(AccountEntity accountEntity){
        List<RoleEntity> roleList = new ArrayList(accountEntity.getRoles());
        RoleEntity roleEntity = new RoleEntity();
        for (RoleEntity role: roleList) {
            roleEntity.setName(role.getName());
        }
        return roleEntity.getName();
    }

    private List<RoleEntity> getRoleFromRoleList(List<Integer> roles) {
        //TODO: throw InvalidParamException if skills_id not Exist
        return roles.stream().map(id -> dbContext.roleRepository.findById(id)
                .orElseThrow(() -> new InvalidParamException(MessageConstants.ForSystem.ENTITY_NOT_FOUND
                        , MessageConstants.ForUser.ROLE_NOT_EXIST
                        , MessageConstants.ResultCode.ENTITY_NOT_FOUND))
        ).collect(Collectors.toList());
    }

    private void validParamRequest(boolean validate, String message) {
        if (!validate) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    , message
                    , MessageConstants.ResultCode.BAD_REQUEST_VALID_PARAMS);
        }
    }

    private boolean isExistedUsername(String userName) {
        return dbContext.accountRepository.existsByUserName(userName);
    }

   public void checkExistedUsername(String username){
       if (isExistedUsername(username)) {
           throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                   , MessageConstants.ForUser.EXISTED_USERNAME
                   , MessageConstants.ResultCode.ENTITY_NOT_FOUND);
       }
   }
    private String saveImageToS3(String img) {
        return Optional.ofNullable(img)
                .filter(x -> !BaseValidationUtil.isEmptyString(x))
                .map(x -> amazonClient.uploadFile(x))
                .orElse(null);
    }
}
