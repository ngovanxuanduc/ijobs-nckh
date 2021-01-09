package com.immortal.internship.entity;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "intern")
public class InternEntity {
    @Id
    @Column(name="id")
    private String id;

    @Column(columnDefinition = "BINARY(16)", name = "student_id")
    private UUID studentID;


    @Column(columnDefinition = "BINARY(16)", name = "company_id")
    private UUID companyID;
    
    @Column(name = "position")
    private String position;





}
