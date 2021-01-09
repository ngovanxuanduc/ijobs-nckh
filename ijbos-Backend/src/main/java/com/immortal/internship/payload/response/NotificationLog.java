package com.immortal.internship.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationLog {
    private String id;
    private String title;
    private String content;
    //1 da xem | 2 chua xem
    private int status;
}
