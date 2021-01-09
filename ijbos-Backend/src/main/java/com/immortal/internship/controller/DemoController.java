package com.immortal.internship.controller;

import com.immortal.internship.aop.annotation.TrackExecutionTime;
import com.immortal.internship.aop.annotation.TrackExecutionUser;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/v1")
public class DemoController {
    @Autowired
    AccountRepository accountRepository;

    private void printCurrentUser(){
        System.out.println("--- Current User ----");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(((UserDetails)principal).getUsername() +" - "+ ((UserDetails)principal).getAuthorities());
        System.out.println("----------------------");
    }
    @TrackExecutionUser
    @TrackExecutionTime
    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        printCurrentUser();
        accountRepository.findAll().forEach(x -> {
            System.out.print(x.getUserName() + " | ");
            x.getRoles().forEach(System.out::println);
        });
//        accountRepository.findAll().forEach(u -> {
//            System.out.println(u.getUserName() + " | " + u.getNotificationHistories());
//        });
        return  ResponseEntity.ok().body("Hello");
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public ResponseEntity<String> admin(){
        printCurrentUser();
        System.out.println("admin");
        return  ResponseEntity.ok().body("admin");
    }

    @GetMapping("/company")
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "')")
    public ResponseEntity<String> company(){
        printCurrentUser();
        System.out.println("company");
        return  ResponseEntity.ok().body("company");
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "')")
    public ResponseEntity<String> student(){
        printCurrentUser();
        System.out.println("student");
        return  ResponseEntity.ok().body("student");
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_TEACHER + "')")
    public ResponseEntity<String> teacher(){
        printCurrentUser();
        System.out.println("teacher");
        return  ResponseEntity.ok().body("teacher");
    }
}
