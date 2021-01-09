package com.immortal.internship.payload.request;

import com.immortal.internship.aop.annotation.UUID;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class DiscussForm {
    @NotBlank(message = "Recruitment ID not Blank")
    private String recruitmentID;

    @UUID
    @NotNull(message = "Receiver not null !!!")
    private String receiver;
}
