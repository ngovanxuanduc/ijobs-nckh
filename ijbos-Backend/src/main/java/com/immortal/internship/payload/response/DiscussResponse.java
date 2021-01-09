package com.immortal.internship.payload.response;

import com.immortal.internship.entity.DiscussContentEntity;
import com.immortal.internship.entity.DiscussEntity;
import com.immortal.internship.model.DiscussHeader;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiscussResponse {
    private DiscussHeader discuss;
    private List<DiscussContentEntity> content;
}
