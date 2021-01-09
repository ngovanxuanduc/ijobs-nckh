package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "recruitment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentEntity {
    @Id
    private String id;

    @Column(name = "title")
    private String title;

    /**
     * status:
     * -1 : sẽ xoa tin nay khoi db | khong ai co the thay no
     * 0 : chưa phê duyệt | chưa hết hạn
     * 1 : chưa phê duyệt | đã hết hạn
     * 2 : đã phê duyệt | chưa hết hạn
     * 3 : đã phê duyệt | hết hạn
     */
    @Column(name = "status")
    private int status;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "location")
    private Boolean location;

    @Column(name = "location_decription")
    private String locationDescription;


    @Column(name = "created_by")
    private UUID createdBy;

//    @Column(name = "approved_by")
//    private UUID approvedBy;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "type")
    private int type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JoinTable(name = "recruitment_skills",
            joinColumns = @JoinColumn(name = "recruitment_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Collection<SkillsEntity> skills;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @ToString.Exclude
    private TeacherEntity approveBy;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<RecruitmentStudentStatusEntity> recruitmentStudentStatusEntities;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

}
