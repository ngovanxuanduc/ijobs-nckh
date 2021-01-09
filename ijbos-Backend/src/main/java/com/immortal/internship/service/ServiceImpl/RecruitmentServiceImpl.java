package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.constant.*;
import com.immortal.internship.entity.*;
import com.immortal.internship.exception.Base4xxException;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.model.ShortInformationAccount;
import com.immortal.internship.payload.request.CreateRecruitmentForm;
import com.immortal.internship.payload.response.CampaignRecruitmentResponse;
import com.immortal.internship.payload.response.DetailsCampaignRecruitmentResponse;
import com.immortal.internship.payload.response.PageResponse;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.AmazonClient;
import com.immortal.internship.service.RecruitmentService;
import com.immortal.internship.service.UserPrinciple;
import com.immortal.internship.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private DBContext dbContext;

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    @Qualifier("ExceptionCampaignRecruitmentNotExist")
    private Base4xxException exCampaignRecruitmentNotExist;



    @Autowired
    @Qualifier("ExceptionAccessDenied")
    private Base4xxException exAccessDenied;

    @Autowired
    private UserProvider userProvider;

    @Override
    public PageResponse getCampaignRecruitmentSortByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort) {
        UserPrinciple currentAccount = getCurrentAccount();
        /**
         * admin có thể xem tất cả các tin bao gồm đã hết hạn
         * company chỉ xem tin của nó bao gòm tin đã hết hạn
         * sinh viên và giảng viên chỉ xem tin đã được kích hoạt, và chưa hết han.
         * */
        //TODO: validated Param Request
        validParamRequest(PageValidationUtil.isValidatedPageNumber(pageNumber), MessageConstants.ForUser.PAGE_NUMBER_ERROR);
        validParamRequest(PageValidationUtil.isValidatedPageSize(pageSize), MessageConstants.ForUser.PAGE_SIZE_ERROR);
        validParamRequest(RecruitmentValidationUtil.isValidatedSortBy(sortBy), MessageConstants.ForUser.SORT_BY_ERROR);
        validParamRequest(PageValidationUtil.isValidatedSortType(typeSort), MessageConstants.ForUser.TYPE_SORT_ERROR);
        Sort sort = Sort.by(sortBy);
        sort = typeSort.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page data;
        boolean showApproveBy = false;
        UUID id = null;
        if (hasRole(currentAccount, RoleConstants.ROLE_COMPANY)) {
            //lay du lieu theo id
            data = dbContext.recruitmentRepository
                    .findAllByCreatedByAndStatusGreaterThan(
                            currentAccount.getAccount().getId(), RecruitmentConstant.Status.DELETE, pageable);
        } else if (hasRole(currentAccount, RoleConstants.ROLE_ADMIN)) {
            data = dbContext.recruitmentRepository.findAllByAndStatusGreaterThan(RecruitmentConstant.Status.DELETE, pageable);
            showApproveBy = true;
        } else {
            data = dbContext.recruitmentRepository.findAllByStatus(RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED, pageable);
            id = currentAccount.getAccount().getId();
        }
        return new PageResponse(data
                , makeRecruitmentResponse(data.getContent(), showApproveBy, id));
    }

    private List<CampaignRecruitmentResponse> makeRecruitmentResponse(List<RecruitmentEntity> listRec
            , boolean showApproveBy, UUID id) {
        Map<String, Integer> mapRSSE = null;
        if (id != null){
            mapRSSE = dbContext.recruitmentStudentStatusRepository.findAllByStudent_Id(id)
                    .stream().collect(
                            Collectors.toMap(r -> r.getId().getRecruitmentId(), RecruitmentStudentStatusEntity::getState));
        }
        Map<String, Integer> finalMapRSSE = mapRSSE;
        return listRec.stream().map(
                r -> convertRecruitmentWithConditions(r,showApproveBy, finalMapRSSE))
                .collect(Collectors.toList());
    }

    private CampaignRecruitmentResponse convertRecruitmentWithConditions(RecruitmentEntity rec
            , boolean isShowApproveBy, Map<String, Integer> mapRSSE ){
        String approveBy =  isShowApproveBy
                ? Optional.ofNullable(rec.getApproveBy())
                    .map(u -> u.getFirstName().concat(" ").concat(u.getLastName()))
                    .orElse(null)
                : null;
        CampaignRecruitmentResponse response = CampaignRecruitmentResponse.builder()
                .id(rec.getId())
                .title(rec.getTitle())
                .status(rec.getStatus())
                .description(rec.getDescription())
                .startTime(rec.getStartTime())
                .endTime(rec.getEndTime())
                .campaignName(rec.getCampaignName())
                .location(rec.getLocation())
                .locationDescription(rec.getLocationDescription())
                .approvedBy(approveBy)
                .createdBy(getInfUserCreatedRecruitment(rec))
                .imageUrl(rec.getImageUrl())
                .skills(rec.getSkills())
                .numberOfStudentApply(numberOfStudentApply(rec.getId()))
                .createdAt(rec.getCreatedAt())
                .build();

        UUID id = (rec.getType() == RecruitmentConstant.Type.CREATED_BY_SCHOOL)
                ? DuyTanInfConstants.ID : rec.getCreatedBy();
        response.setShortInfoAccount(userProvider.shortInformationCompany(id,dbContext));
        //get bookmark or apply for student
        if (mapRSSE != null){
            response.setStudentBookMarkApply(Optional.ofNullable(mapRSSE.get(rec.getId())).orElse(RecruitmentStudentStatusConstants.NOTHING));
        } else {
            response.setStudentBookMarkApply(RecruitmentStudentStatusConstants.NOTHING);
        }
        return response;
    }

    private long numberOfStudentApply(String idCampaignRecruitment) {
        return dbContext.recruitmentStudentStatusRepository
                .countAllByRecruitment_IdAndStateGreaterThanEqual(idCampaignRecruitment, RecruitmentStudentStatusConstants.APPLY);
    }

    private BaseAccountEntity getInfUserCreatedRecruitment(RecruitmentEntity r) {
        return r.getType() == RecruitmentConstant.Type.CREATED_BY_COMPANY
                ? dbContext.companyRepository.findById(r.getCreatedBy()).orElse(null)
                : dbContext.teacherRepository.findById(r.getCreatedBy()).orElse(null);
    }

    @Override
    public RecruitmentEntity getRecruitmentById(String idCr) {
        return Optional.ofNullable(dbContext.recruitmentRepository
                .findByIdAndStatusEquals(idCr, RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED))
                .orElseThrow(()->exCampaignRecruitmentNotExist);
    }

    @Override
    public RecruitmentEntity createCampaignRecruitment(CreateRecruitmentForm crf) {
        //lay ra user hien tai
        UserPrinciple currentAccount = getCurrentAccount();
        return createCampaignRecruitmentOnDB(crf, currentAccount);
    }

    @Override
    public RecruitmentEntity updateRecruitment(String idUpdate, CreateRecruitmentForm update) {
        UserPrinciple currentUser = getCurrentAccount();
        RecruitmentEntity oldRecruitment = getOldCampaignRecruitment(idUpdate, currentUser);
        oldRecruitment.setStatus(RecruitmentConstant.Status.DELETE_FOREVER);
        return createCampaignRecruitmentOnDB(update, currentUser);
    }

    @Override
    public String deleteRecruitment(String id) {
        UserPrinciple currentUser = getCurrentAccount();
        RecruitmentEntity oldRecruitment = getOldCampaignRecruitment(id, currentUser);
        //truong hop khong xoa duoc
        //TODO: check in checkCampaignRecruitmentDelete
//        if (oldRecruitment.getStatus() <= RecruitmentConstant.Status.DELETE) {
//            throw new InvalidParamException(MessageConstants.ForSystem.ENTITY_NOT_FOUND
//                    , MessageConstants.ForUser.RECRUITMENT_NOT_EXIST
//                    , MessageConstants.ResultCode.ENTITY_NOT_FOUND);
//        }

        oldRecruitment.setStatus(RecruitmentConstant.Status.DELETE);
        dbContext.recruitmentRepository.save(oldRecruitment);
        return MessageConstants.ForUser.DELETE_RECRUITMENT;
    }

    @Override
    public String approveRecruitment(String id) {
        UserPrinciple currentUser = getCurrentAccount();
        RecruitmentEntity oldRecruitment = getOldCampaignRecruitmentAdmin(id);

//        if (oldRecruitment.getStatus() <= RecruitmentConstant.Status.DELETE) {
////            throw new InvalidParamException(MessageConstants.ForSystem.ENTITY_NOT_FOUND
////                    , MessageConstants.ForUser.RECRUITMENT_NOT_EXIST
////                    , MessageConstants.ResultCode.ENTITY_NOT_FOUND);
//            throw exCampaignRecruitmentNotExist;
//        }
        oldRecruitment.setStatus(RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED);
        oldRecruitment.setApproveBy(currentUser.getAccount().getTeacher());
        dbContext.recruitmentRepository.save(oldRecruitment);
        return MessageConstants.ForUser.APPROVE_RECRUITMENT;
    }

    @Override
    public DetailsCampaignRecruitmentResponse getCampaignRecruitmentByID(String idCR) {
        UserPrinciple currentAccount = getCurrentAccount();
        RecruitmentEntity oldCR = getOldCampaignRecruitment(idCR, currentAccount);
        DetailsCampaignRecruitmentResponse result = DetailsCampaignRecruitmentResponse.builder()
                .campaignRecruitment(convertRecruitmentWithConditions(oldCR,hasRole(currentAccount,RoleConstants.ROLE_ADMIN),null))
                .build();
        if (!hasRole(currentAccount,RoleConstants.ROLE_STUDENT)){
            result.setStudents(shortInformationStudentList(idCR));
        } else {
            if (oldCR.getStatus() < RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED){
                throw exCampaignRecruitmentNotExist;
            }
            int statusOfStudentOnRecruitment = dbContext.recruitmentStudentStatusRepository
                    .findByRecruitment_IdAndStudent_Id(oldCR.getId(),currentAccount.getAccount().getId())
                    .map(RecruitmentStudentStatusEntity::getState)
                    .orElse(RecruitmentStudentStatusConstants.NOTHING);
            result.getCampaignRecruitment().setStudentBookMarkApply(statusOfStudentOnRecruitment);
        }
        return result;
    }

    @Override
    public List<RecruitmentEntity> getRecruitmentsByCreateBy(UUID id) {
        return Optional.ofNullable(dbContext.recruitmentRepository
                .findAllByCreatedByAndStatusGreaterThanEqual(id, RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED))
                .orElseThrow(()->exCampaignRecruitmentNotExist);
    }

    private List<ShortInformationAccount> shortInformationStudentList(String idCR) {
        return dbContext.recruitmentStudentStatusRepository
                .findAllByRecruitment_IdAndStateGreaterThanEqual(idCR, RecruitmentStudentStatusConstants.APPLY )
                .stream()
                .map(u -> convertSTEntityToShortInfST(u.getStudent()))
                .collect(Collectors.toList());
    }

    private ShortInformationAccount convertSTEntityToShortInfST(StudentEntity st) {
        return ShortInformationAccount.builder()
                .id(st.getId())
                .images(dbContext.imageRepository.findById(st.getId())
                        .orElseGet(ImageEntity::new))
                .fullName(st.getFirstName().concat(" ").concat(st.getLastName()))
                .build();
    }

    private RecruitmentEntity getOldCampaignRecruitmentAdmin(String id) {
        return checkCampaignRecruitmentDelete(
                dbContext.recruitmentRepository.findById(id)
                        .orElseThrow(() -> exCampaignRecruitmentNotExist));
    }

    private RecruitmentEntity checkCampaignRecruitmentDelete(RecruitmentEntity oldCampaignRecruitment) {
        return Optional.ofNullable(oldCampaignRecruitment)
                .filter(r -> r.getStatus() > RecruitmentConstant.Status.DELETE)
                .orElseThrow(() -> exCampaignRecruitmentNotExist);
    }

    private RecruitmentEntity getOldCampaignRecruitment(String id, UserPrinciple user) {
        if (hasRole(user, RoleConstants.ROLE_COMPANY)) {
            return checkCampaignRecruitmentDelete(
                    dbContext.recruitmentRepository.findByIdAndCreatedBy(id, user.getAccount().getId())
                            .orElseThrow(() -> exAccessDenied));
        }
        return getOldCampaignRecruitmentAdmin(id);
    }

    private List<SkillsEntity> getSkillsEntityFromSkills(List<Integer> skills) {
        //TODO: throw InvalidParamException if skills_id not Exist
        return skills.stream().map(id -> dbContext.skillsRepository.findById(id)
                .orElseThrow(() -> new InvalidParamException(MessageConstants.ForSystem.ENTITY_NOT_FOUND
                        , MessageConstants.ForUser.SKILL_NOT_EXIST, MessageConstants.ResultCode.ENTITY_NOT_FOUND))
        ).collect(Collectors.toList());
    }

    private UserPrinciple getCurrentAccount() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean hasRole(UserPrinciple user, String role) {
        return user.getAuthorities().stream()
                .anyMatch(r -> (role.equals(r.toString())));
    }

    private void validParamRequest(boolean validatedParam, String messErrorForUser) {
        if (!validatedParam) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    , messErrorForUser
                    , MessageConstants.ResultCode.BAD_REQUEST_VALID_PARAMS);
        }
    }

    private String generateIDRecruitment() {
        String res;
        do {
            res = RandomIDUtil.getGeneratedString();
        } while (dbContext.recruitmentRepository.existsById(res));
        return res;
    }

    private boolean isSchoolAccount(UserPrinciple u) {
        return u.getAuthorities().stream()
                .anyMatch(r -> (RoleConstants.ROLE_ADMIN.equals(r.toString())
                        || RoleConstants.ROLE_TEACHER.equals(r.toString())));
    }

    private int getTypeCampaignRecruitment(UserPrinciple u) {
        return isSchoolAccount(u)
                ? RecruitmentConstant.Type.CREATED_BY_SCHOOL
                : RecruitmentConstant.Type.CREATED_BY_COMPANY;
    }

    private RecruitmentEntity createCampaignRecruitmentOnDB(CreateRecruitmentForm crf, UserPrinciple currentAccount) {
        String imgURL = saveImageToS3(crf.getImage());
        RecruitmentEntity newRecruitment = RecruitmentEntity.builder()
                .id(generateIDRecruitment())
                .title(crf.getTitle())
                .status(0)
                .imageUrl(imgURL)
                .description(crf.getDescription())
                .startTime(crf.getStartTime())
                .endTime(crf.getEndTime())
                .campaignName(crf.getCampaignName())
                .location(crf.getLocation())
                .locationDescription(crf.getLocationDescription())
                .createdBy(currentAccount.getAccount().getId())
                .skills(getSkillsEntityFromSkills(crf.getSkills()))
                .type(getTypeCampaignRecruitment(currentAccount))
                .build();
        dbContext.recruitmentRepository.save(newRecruitment);
        return newRecruitment;
    }

    private String saveImageToS3(String img) {
        return Optional.ofNullable(img)
                .filter(x -> !BaseValidationUtil.isEmptyString(x))
                .map(x -> amazonClient.uploadFile(x))
                .orElse(null);
    }

    @Override
    public PageResponse getRecruitmentPublic(Integer pageNumber, Integer pageSize) {
        //TODO: validated Param Request
        validParamRequest(PageValidationUtil.isValidatedPageNumber(pageNumber), MessageConstants.ForUser.PAGE_NUMBER_ERROR);
        validParamRequest(PageValidationUtil.isValidatedPageSize(pageSize), MessageConstants.ForUser.PAGE_SIZE_ERROR);
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<RecruitmentEntity> data = dbContext.recruitmentRepository
                .findAllByStatus(RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED,pageable);
        List<CampaignRecruitmentResponse> recruitments = data.getContent()
                .stream().map(this::convertRecruitmentPublic)
                .collect(Collectors.toList());

        return new PageResponse(data, recruitments);
    }

    private CampaignRecruitmentResponse convertRecruitmentPublic(RecruitmentEntity rec){
        CampaignRecruitmentResponse response = CampaignRecruitmentResponse.builder()
                .id(rec.getId())
                .title(rec.getTitle())
                .status(rec.getStatus())
                .description(rec.getDescription())
                .startTime(rec.getStartTime())
                .endTime(rec.getEndTime())
                .campaignName(rec.getCampaignName())
                .location(rec.getLocation())
                .locationDescription(rec.getLocationDescription())
                .approvedBy(null)
                .imageUrl(rec.getImageUrl())
                .skills(rec.getSkills())
                .numberOfStudentApply(0)
                .createdAt(rec.getCreatedAt())
                .build();

        UUID id = (rec.getType() == RecruitmentConstant.Type.CREATED_BY_SCHOOL)
                ? DuyTanInfConstants.ID : rec.getCreatedBy();
        response.setShortInfoAccount(userProvider.shortInformationCompany(id,dbContext));

        return response;
    }
}
