package com.immortal.internship.payload.request;

import lombok.Data;

import javax.validation.constraints.*;


@Data
public class CreateNotification {

    @NotBlank(message = "Tittle must not be blank")
    private String title;

    @NotBlank(message = "Over View must not be blank")
    private String overView;

    //@Min(value = 1, message = "Group Id should not be less 1")
    @NotNull(message = "Group Id should not be null")
    private Integer groupId;

}
