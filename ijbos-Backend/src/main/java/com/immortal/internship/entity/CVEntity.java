package com.immortal.internship.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name="curriculum_vitae")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CVEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(columnDefinition = "BINARY(16)", name = "student_id")
    private UUID studentID;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "objective")
    private String objective;

    @Column(name = "certifications")
    private String certifications;

    @Column(name = "honors_awards")
    private String honorsAwards;

    @Column(name = "interests")
    private String interests;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "work_experience")
    private String workExperience;

    @Column(name = "education")
    private String education;

    @Column(name = "activities")
    private String activities;

    @Column(name = "refer")
    private String refer;

    @Column(columnDefinition="tinyint(1) default false", name = "active")
    private boolean active;

    @Column(columnDefinition = "BINARY(16)", name = "approved_by")
    private UUID approvedBy;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JoinTable(name = "cv_skills",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Collection<SkillsEntity> skillsCV;

}
