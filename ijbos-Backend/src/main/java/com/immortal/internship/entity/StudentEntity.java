package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class StudentEntity implements BaseAccountEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)", name = "account_id")
    private UUID id;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "school_year")
    private String schoolYear;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "department")
    private String department;

    @Column(name = "klass")
    private String klass;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<RecruitmentStudentStatusEntity> recruitmentStudentStatusEntities;




}

