package com.immortal.internship.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussFullResponse {
    private String recId;
    private String recTittle;
    private String discussId;
    private String ownerName;
    private UUID ownerId;
    private String receiverName;
    private UUID receiverId;
    List<DiscussContentFull> listContent;

}
