package com.immortal.internship.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentStudentStatusKey implements Serializable {

    @Column(name = "recruitment_id")
    private String recruitmentId;

    @Column(name = "student_id")
    private UUID studentId;

}
