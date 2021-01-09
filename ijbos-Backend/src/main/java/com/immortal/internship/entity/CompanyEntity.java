package com.immortal.internship.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyEntity implements BaseAccountEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)", name = "account_id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "company_info")
    private String companyInfo;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "working_date")
    private String workingDate;

    @Column(name = "country")
    private String country;

    @Column(name = "over_view")
    private String overView;


}
