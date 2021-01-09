package com.immortal.internship.entity;

import com.immortal.internship.entity.key.RecruitmentStudentStatusKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "recruitment_student_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentStudentStatusEntity {

    @EmbeddedId
    private RecruitmentStudentStatusKey id = new RecruitmentStudentStatusKey();

    @ManyToOne
    @MapsId("recruitmentId")
    @JoinColumn(name = "recruitment_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RecruitmentEntity recruitment;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private StudentEntity student;

    @Column(name = "state")
    private int state;

    @Override
    public String toString() {
        return "RecruitmentStudentStatusEntity{" +
                " | ID=" + id +
                " | RecruitmentID=" + recruitment.getId() +
                " | RecruitmentTitle=" + recruitment.getTitle() +
                " | StudentID=" + student.getId() +
                " | StudentName=" + student.getFirstName().concat(" ").concat(student.getLastName()) +
                " | state=" + state +
                '}';
    }
}
