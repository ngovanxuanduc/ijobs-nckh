package com.immortal.internship.utility;

import com.immortal.internship.constant.DuyTanInfConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.entity.CompanyEntity;
import com.immortal.internship.entity.ImageEntity;
import com.immortal.internship.model.ShortInformationAccount;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.UUID;

@Component
public class UserProvider {
    public UserPrinciple getCurrentUser(){
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean hasRole(UserPrinciple user, String role) {
        return user.getAuthorities().stream()
                .anyMatch(r -> (role.equals(r.toString())));
    }

    public boolean isSchoolAccount(UserPrinciple u) {
        return u.getAuthorities().stream()
                .anyMatch(r -> (RoleConstants.ROLE_ADMIN.equals(r.toString())
                        || RoleConstants.ROLE_TEACHER.equals(r.toString())));
    }

    public boolean isStudentAccount(UserPrinciple u){
        return hasRole(u,RoleConstants.ROLE_STUDENT);
    }

    public boolean isCompanyAccount(UserPrinciple u){
        return hasRole(u, RoleConstants.ROLE_COMPANY);
    }

    public ShortInformationAccount duyTanShortInf(DBContext dbContext){
        return dbContext.companyRepository.findById(DuyTanInfConstants.ID)
                .map(com -> ShortInformationAccount.builder()
                        .id(com.getId())
                        .fullName(com.getName())
                        .images(dbContext.imageRepository.findById(DuyTanInfConstants.ID).orElseGet(ImageEntity::new))
                        .build())
                .orElse(null);
    }

    public ShortInformationAccount shortInformationCompany(UUID id, DBContext dbContext){
        return dbContext.companyRepository.findById(id)
                .map(com -> ShortInformationAccount.builder()
                        .id(com.getId())
                        .fullName(com.getName())
                        .images(dbContext.imageRepository.findById(id).orElseGet(ImageEntity::new))
                        .build())
                .orElse(null);
    }
}
