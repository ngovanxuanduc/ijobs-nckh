package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherEntity implements BaseAccountEntity{

    @Id
    @Column(columnDefinition = "BINARY(16)", name = "account_id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address")
    private String address;

    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "approveBy",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @JsonBackReference
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private Collection<RecruitmentEntity> recruitmentEntities;
}

