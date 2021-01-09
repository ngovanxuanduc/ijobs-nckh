package com.immortal.internship.payload.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Data
public class CVForm {

    @NotBlank(message = "Recruitment ID not Blank")
    private String contactInfo;

    @NotBlank(message = "Recruitment ID not Blank")
    private String objective;

    @NotBlank(message = "Recruitment ID not Blank")
    private String certifications;

    @NotBlank(message = "Recruitment ID not Blank")
    private String honorsAwards;

    @NotBlank(message = "Recruitment ID not Blank")
    private String interests;

    @NotBlank(message = "Recruitment ID not Blank")
    private String additionalInfo;

    @NotBlank(message = "Recruitment ID not Blank")
    private String workExperience;

    @NotBlank(message = "Recruitment ID not Blank")
    private String education;

    @NotBlank(message = "Recruitment ID not Blank")
    private String activities;

    @NotBlank(message = "Recruitment ID not Blank")
    private String refer;

    @NotBlank(message = "Skills not Blank")
    private List<Integer> skills;
}
