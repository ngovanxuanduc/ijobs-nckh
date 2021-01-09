package com.immortal.internship.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussAllResponse {
    private String id;
    private String recruitmentName;
    private String ownerName;
    private String receiverName;
    private int status;
    private Timestamp createdAt;
}
