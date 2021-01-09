package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.constant.DiscussStatus;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.entity.DiscussContentEntity;
import com.immortal.internship.entity.DiscussEntity;
import com.immortal.internship.exception.Base4xxException;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.model.DiscussHeader;
import com.immortal.internship.payload.response.DiscussFullResponse;
import com.immortal.internship.payload.response.DiscussResponse;
import com.immortal.internship.payload.response.DiscussAllResponse;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.utility.BaseValidationUtil;
import com.immortal.internship.utility.RandomIDUtil;
import com.immortal.internship.utility.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class DiscussServiceImpl implements DiscussService {

    @Autowired
    private DBContext dbContext;

    @Autowired
    @Qualifier("ExceptionAccessDenied")
    private Base4xxException exceptionAccessDenied;

    @Autowired
    @Qualifier("ExceptionCampaignRecruitmentNotExist")
    private Base4xxException exceptionCampaignRecruitmentNotExist;

    @Autowired
    private UserProvider userProvider;

    @Override
    public String addReply(String discussId, String content) {
        //TODO: check discuss id Exist ? , check owner , Save discuss content
        CheckOwnerDiscuss(discussId, userProvider.getCurrentUser().getAccount().getId());
        validParamRequest(!BaseValidationUtil.isEmptyString(content),MessageConstants.ForUser.CONTENT_NOT_NULL);
        //check status discuss
        if (!dbContext.discussRepository.existsDistinctByIdAndStatusEquals(discussId, DiscussStatus.OPENED)){
            throw new Base4xxException(MessageConstants.ForSystem.DISCUSS_CLOSED,
                    MessageConstants.ForUser.DISCUSS_CLOSED,
                    MessageConstants.ResultCode.FORBIDDEN );
        }
        //storage discuss content
        DiscussContentEntity newMess = DiscussContentEntity.builder()
                .content(content)
                .discussID(discussId)
                .owner(userProvider.getCurrentUser().getAccount().getId())
                .build();
        dbContext.discussContentRepository.save(newMess);
        return MessageConstants.ForUser.SAVE_DISCUSS_CONTENT_OK;
    }

    private void CheckOwnerDiscuss(String discussId, UUID user) {
        //TODO: check whether users are in the conversation
        if (!dbContext.customRepository.isUserExistOnDiscuss(discussId, user)) {
            throw exceptionAccessDenied;
        }
    }

    @Override
    public DiscussEntity createDiscuss(String recruitmentID, UUID receiver) {
        UUID idOwner = userProvider.getCurrentUser().getAccount().getId();

        //Discuss Exist
        DiscussEntity dump = dbContext.discussRepository.findByOwnerAndReceiverAndRecruitmentID(idOwner,receiver,recruitmentID);
        if ( dump != null){
            return dump;
        }

        if (idOwner.equals(receiver)){
            throw new Base4xxException(MessageConstants.ForSystem.SAME_USER,
                    MessageConstants.ForSystem.SAME_USER,
                    MessageConstants.ResultCode.FORBIDDEN );
        }
        if (!dbContext.recruitmentRepository.existsById(recruitmentID)){
            throw exceptionCampaignRecruitmentNotExist;
        }
        DiscussEntity discuss = DiscussEntity.builder()
                .owner(userProvider.getCurrentUser().getAccount().getId())
                .receiver(receiver)
                .id(RandomIDUtil.getGeneratedString(17))
                .status(DiscussStatus.OPENED)
                .recruitmentID(recruitmentID)
                .build();
        return dbContext.discussRepository.save(discuss);
    }

    @Override
    public DiscussEntity closedDiscuss(String discussId) {
        //TODO: check discuss id Exist ? , check owner , Save discuss status
        CheckOwnerDiscuss(discussId, userProvider.getCurrentUser().getAccount().getId());
        Optional<DiscussEntity> discuss = dbContext.discussRepository.findById(discussId);
        discuss.get().setStatus(DiscussStatus.CLOSED);
        return dbContext.discussRepository.save(discuss.get());
    }

    @Override
    public DiscussResponse getDiscussByID(String discussID) {
        CheckOwnerDiscuss(discussID, userProvider.getCurrentUser().getAccount().getId());
        DiscussHeader header = dbContext.customRepository.getDiscussHeaderByID(discussID);
        List<DiscussContentEntity> content = dbContext.discussContentRepository
                .findAllByDiscussIDOrderByCreatedAtDesc(discussID)
                .orElseGet(ArrayList::new);
        return DiscussResponse.builder()
                .content(content)
                .discuss(header)
                .build();
    }

    @Override
    public List<DiscussAllResponse> getAllDiscuss() {
        UUID id = userProvider.getCurrentUser().getAccount().getId();
        return Optional.ofNullable(dbContext.customRepository.getAllDiscussByOwner(id))
        .orElseGet(ArrayList::new);
    }

    @Override
    public DiscussFullResponse getDetailDiscussById(String id) {
        return Optional.ofNullable(dbContext.customRepository.getFullDiscuss(id))
                .orElseGet(null);
    }

    private void validParamRequest(boolean validatedParam, String messErrorForUser) {
        if (!validatedParam) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    , messErrorForUser
                    , MessageConstants.ResultCode.BAD_REQUEST_VALID_PARAMS);
        }
    }
}
