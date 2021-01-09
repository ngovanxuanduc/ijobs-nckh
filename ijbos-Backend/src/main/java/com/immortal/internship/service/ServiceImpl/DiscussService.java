package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.entity.DiscussEntity;
import com.immortal.internship.payload.response.DiscussFullResponse;
import com.immortal.internship.payload.response.DiscussResponse;
import com.immortal.internship.payload.response.DiscussAllResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface DiscussService {
    String addReply(String discussId,String content);

    DiscussEntity createDiscuss(String recruitmentID,UUID receiver);

    DiscussEntity closedDiscuss(String discussId);

    DiscussResponse getDiscussByID(String discussID);

    List<DiscussAllResponse> getAllDiscuss();

    DiscussFullResponse getDetailDiscussById(String id);
}
