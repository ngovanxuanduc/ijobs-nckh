package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.constant.CVConstant;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.RecruitmentStudentStatusConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.entity.*;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.payload.request.CVForm;
import com.immortal.internship.payload.response.StudentCVResponse;
import com.immortal.internship.payload.response.StudentProfileResponse;
import com.immortal.internship.repository.CVRepository;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.CVService;
import com.immortal.internship.service.UserPrinciple;
import com.immortal.internship.utility.RandomIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CVServiceImp implements CVService {


    @Autowired
    DBContext dbContext;

    @Autowired
    @Qualifier("ExceptionCVNotFound")
    private InvalidParamException exCVNotExist;

    @Autowired
    @Qualifier("ExceptionAccountNotFound")
    InvalidParamException exAccountNotFound;


    @Override
    public CVEntity getCVById(UUID id) {
        return Optional.ofNullable(dbContext.cvRepository.findByStudentID(id)).orElseThrow(()->exCVNotExist);
    }

    @Override
    public CVEntity getCV() {
        UserPrinciple currentAccount = getCurrentAccount();
        return Optional.ofNullable(dbContext.cvRepository
                .findByStudentID(currentAccount.getAccount().getStudent().getId())).orElseThrow(()->exCVNotExist);
    }

    @Override
    public CVEntity getActiveCVById(UUID id) {
        return Optional.ofNullable(dbContext.cvRepository
                .findByStudentIDAndActiveGreaterThanEqual(id, CVConstant.ACTIVE))
                .orElseThrow(()->exCVNotExist);
    }
    @Override
    public String acceptCV(UUID stId, String reId) {
        UserPrinciple currentAccount = getCurrentAccount();
        InternEntity internEntity = InternEntity.builder()
                .id(generateID())
                .companyID(currentAccount.getAccount().getId())
                .studentID(stId)
                .position("Intern")
                .build();
        System.out.println(internEntity);

        dbContext.internRepository.save(internEntity);
        toNothing(stId, reId);
        return MessageConstants.ForUser.ACCEPT_CV_SUCCESS;
    }


    @Override
    public String rejectCV(UUID stId, String reId) {
        toNothing(stId, reId);
        return MessageConstants.ForUser.REJECT_CV_SUCCESS;
    }

    private void toNothing(UUID stId, String reId){
        Optional.ofNullable(dbContext.recruitmentStudentStatusRepository
                .findByStudentIdAndRecruitmentIdAndStateGreaterThanEqual(stId, reId, RecruitmentStudentStatusConstants.APPLY)).orElseThrow(()->exAccountNotFound);
        RecruitmentStudentStatusEntity recruitmentStudentStatusEntity = dbContext.recruitmentStudentStatusRepository
                .findByStudentIdAndRecruitmentIdAndStateGreaterThanEqual(stId, reId, RecruitmentStudentStatusConstants.APPLY);
        recruitmentStudentStatusEntity.setState(RecruitmentStudentStatusConstants.NOTHING);
        dbContext.recruitmentStudentStatusRepository.save(recruitmentStudentStatusEntity);
    }
    @Override
    public CVEntity createCV(CVForm cvForm) {
        UserPrinciple currentAccount = getCurrentAccount();
        checkExistedCV(currentAccount);
        CVEntity cvEntity = CVEntity.builder()
                .id(generateID())
                .studentID(currentAccount.getAccount().getStudent().getId())
                .additionalInfo(cvForm.getAdditionalInfo())
                .activities(cvForm.getActivities())
                .certifications(cvForm.getCertifications())
                .contactInfo(cvForm.getContactInfo())
                .education(cvForm.getEducation())
                .honorsAwards(cvForm.getHonorsAwards())
                .interests(cvForm.getInterests())
                .objective(cvForm.getObjective())
                .refer(cvForm.getRefer())
                .workExperience(cvForm.getWorkExperience())
                .active(CVConstant.NOT_ACTIVE)
                .skillsCV(getSkillsEntityFromSkills(cvForm.getSkills()))
                .approvedBy(null)
                .build();
//        System.out.println(cvEntity);
        dbContext.cvRepository.save(cvEntity);
        return cvEntity;
    }

    @Override
    public List<StudentCVResponse> getStudentList() {
        return Optional.ofNullable(dbContext.customRepository.getStudents()).orElseThrow(()->exCVNotExist);
    }

    @Override
    public String cancelCV(UUID id) {
        CVEntity cv = dbContext.cvRepository.findByStudentID(id);
        Optional.ofNullable(cv).orElseThrow(()->exCVNotExist);
        UserPrinciple currentUser = getCurrentAccount();
        cv.setActive(CVConstant.NOT_ACTIVE);
        cv.setApprovedBy(null);
        dbContext.cvRepository.save(cv);
        return MessageConstants.ForUser.REJECT_CV_SUCCESS;
    }

    @Override
    public String approveCV(UUID id) {
        CVEntity cv = dbContext.cvRepository.findByStudentID(id);
        Optional.ofNullable(cv).orElseThrow(()->exCVNotExist);
        UserPrinciple currentUser = getCurrentAccount();
//        System.out.println(currentUser.getAccount().getId());
        cv.setActive(CVConstant.ACTIVE);
        cv.setApprovedBy(currentUser.getAccount().getId());
        dbContext.cvRepository.save(cv);
        return MessageConstants.ForUser.APPROVED_CV_SUCCESS;
    }

    @Override
    public List<StudentEntity> getListStudentIntern() {
        UserPrinciple currentUser = getCurrentAccount();


        return null;
    }

    @Override
    public String updateCV(CVForm cvForm) {

        return null;
    }

    private void checkExistedCV(UserPrinciple currentAccount){
        if(dbContext.cvRepository.existsByStudentID(currentAccount.getAccount().getStudent().getId())){
            throw new InvalidParamException (
                    MessageConstants.ForSystem.SAME_USER,
                    MessageConstants.ForUser.EXISTED_CV,
                    MessageConstants.ResultCode.EXISTED_USER
            );
        };
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

    private String generateID() {
        String res;
        do {
            res = RandomIDUtil.getGeneratedString();
        } while (dbContext.cvRepository.existsById(res));
        return res;
    }


}
