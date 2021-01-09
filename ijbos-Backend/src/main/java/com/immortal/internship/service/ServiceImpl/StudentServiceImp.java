package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.RecruitmentConstant;
import com.immortal.internship.constant.RecruitmentStudentStatusConstants;
import com.immortal.internship.entity.RecruitmentEntity;
import com.immortal.internship.entity.RecruitmentStudentStatusEntity;
import com.immortal.internship.entity.StudentEntity;
import com.immortal.internship.entity.key.RecruitmentStudentStatusKey;
import com.immortal.internship.exception.Base4xxException;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.StudentService;
import com.immortal.internship.utility.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private DBContext dbContext;

    @Autowired
    @Qualifier("ExceptionCampaignRecruitmentNotExist")
    private Base4xxException exCampaignRecruitmentNotExist;

    @Override
    public String applyCampaignRecruitment(String idCR) {

        RecruitmentStudentStatusEntity log = getLogOnDb(idCR, userProvider.getCurrentUser().getAccount().getId());
        if (log == null) {
            createStateInDB(idCR, RecruitmentStudentStatusConstants.APPLY);
            return MessageConstants.ForUser.APPLY_SUCCESS;
        }
        updateStateFull(log, RecruitmentStudentStatusConstants.APPLY);
        return MessageConstants.ForUser.APPLY_SUCCESS;
    }

    @Override
    public String bookMarkCampaignRecruitment(String idCR) {
        RecruitmentStudentStatusEntity log = getLogOnDb(idCR, userProvider.getCurrentUser().getAccount().getId());
        if (log == null) {
            createStateInDB(idCR, RecruitmentStudentStatusConstants.BOOK_MARK);
            return MessageConstants.ForUser.BOOKMARK_SUCCESS;
        }
        updateStateFull(log, RecruitmentStudentStatusConstants.BOOK_MARK);
        return MessageConstants.ForUser.BOOKMARK_SUCCESS;
    }

    @Override
    public String unApplyCampaignRecruitment(String idCR) {
        unUpdateStateFull(idCR,RecruitmentStudentStatusConstants.APPLY);
        return MessageConstants.ForUser.UN_APPLY_SUCCESS;
    }

    @Override
    public String unBookMarkCampaignRecruitment(String idCR) {
        unUpdateStateFull(idCR,RecruitmentStudentStatusConstants.BOOK_MARK);
        return MessageConstants.ForUser.UN_BOOKMARK_SUCCESS;
    }

    private void unUpdateStateFull(String idCR, int oldState) {
        RecruitmentStudentStatusEntity log = getLogOnDb(idCR, userProvider.getCurrentUser().getAccount().getId());
        if (log != null) {
            if (log.getState() == RecruitmentStudentStatusConstants.BOOK_MARK_AND_APPLY) {
                int newState = (oldState == RecruitmentStudentStatusConstants.APPLY)
                        ? RecruitmentStudentStatusConstants.BOOK_MARK
                        : RecruitmentStudentStatusConstants.APPLY;

                log.setState(newState);
            } else if (log.getState() == oldState) {
                log.setState(RecruitmentStudentStatusConstants.NOTHING);
            }
            dbContext.recruitmentStudentStatusRepository.save(log);
        }
    }

    @Override
    public List<String> bookMarks() {
        return getListBookMarksOrApply(RecruitmentStudentStatusConstants.BOOK_MARK);
    }

    @Override
    public List<String> listApply() {
        return getListBookMarksOrApply(RecruitmentStudentStatusConstants.APPLY);
    }


    private List<String> getListBookMarksOrApply(int status){
        return dbContext.recruitmentStudentStatusRepository.findAllByStudent_Id(userProvider.getCurrentUser().getAccount().getId())
                .stream()
                .filter(log ->(
                        log.getState() == status
                                || log.getState() == RecruitmentStudentStatusConstants.BOOK_MARK_AND_APPLY))
                .map(RecruitmentStudentStatusEntity::getId)
                .map(RecruitmentStudentStatusKey::getRecruitmentId)
                .collect(Collectors.toList());
    }

    private void createStateInDB(String idCR, int state) {
        RecruitmentStudentStatusEntity infApply = RecruitmentStudentStatusEntity.builder()
                .id(new RecruitmentStudentStatusKey(idCR, userProvider.getCurrentUser().getAccount().getId()))
                .recruitment(getCampaignRecruitment(idCR))
                .state(state)
                .student((StudentEntity) userProvider.getCurrentUser().getAccount().getInformationAccount())
                .build();
        dbContext.recruitmentStudentStatusRepository.save(infApply);
    }

    private RecruitmentEntity getCampaignRecruitment(String idCR) {
        return dbContext.recruitmentRepository.findById(idCR)
                .filter(r -> r.getStatus() == RecruitmentConstant.Status.ACTIVE_NOT_EXPIRED)
                .orElseThrow(() -> exCampaignRecruitmentNotExist);
    }

    private RecruitmentStudentStatusEntity getLogOnDb(String idCR, UUID userID) {
        return dbContext.recruitmentStudentStatusRepository.findByRecruitment_IdAndStudent_Id(idCR, userID)
                .orElse(null);
    }

    private void updateStateFull(RecruitmentStudentStatusEntity log, int newState) {
        if (log.getState() == RecruitmentStudentStatusConstants.NOTHING){
            log.setState(newState);
        } else if (log.getState() != newState){
            log.setState(RecruitmentStudentStatusConstants.BOOK_MARK_AND_APPLY);
        }
        dbContext.recruitmentStudentStatusRepository.save(log);
    }

}
