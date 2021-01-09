package com.immortal.internship.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "current_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentTokenEntity {
    @Id
    @Column(name = "account_user_name")
    private String userName;
    @Column(name = "web_token")
    private String webToken;
    @Column(name = "mobile_token")
    private String mobileToken;
}
