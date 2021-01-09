package com.immortal.internship.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternDetail {
   private String fullName;
   private String email;
   private String phoneNumber;
   private String address;
   private Date birthDay;
   private String position;
   private String companyName;
}
