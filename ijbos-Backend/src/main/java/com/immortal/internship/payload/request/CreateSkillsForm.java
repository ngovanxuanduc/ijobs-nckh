package com.immortal.internship.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateSkillsForm {

    @NotEmpty(message = "skills cannot empty")
    private List<@NotBlank(message="Skill must not be blank") String> skills;
}
