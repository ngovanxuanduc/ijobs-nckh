package com.immortal.internship.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussContentFull {
    private long id;
    private String discussID;
    private UUID owner;
    private String ownerImage;
    private String ownerName;
    private String receiverImage;
    private String content;
    private LocalDateTime createdAt;
}
