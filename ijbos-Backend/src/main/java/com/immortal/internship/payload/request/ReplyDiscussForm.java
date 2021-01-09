package com.immortal.internship.payload.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReplyDiscussForm {
    @NotBlank(message = "Discuss_ID Can't Not Blank")
    private String discussID;
    @NotBlank(message = "Content Can't Blank")
    private String content;
}
