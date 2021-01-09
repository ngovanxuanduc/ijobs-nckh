package com.immortal.internship.service;

import com.immortal.internship.constant.AccountActiveConstants;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.Prefix;
import com.immortal.internship.entity.AccountEntity;
import com.immortal.internship.entity.StudentEntity;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.repository.DBContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    AccountEntity account;


//    @Autowired
//    DBContext dbContext;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = account.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(Prefix.ROLE.concat(role.getName()))
                ).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getStatus()!=AccountActiveConstants.BANED;
    }

    public boolean isProfileComplete(){
        return account.getStatus() == AccountActiveConstants.NORMAL_ACCOUNT;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        UserPrinciple user = (UserPrinciple) o;
//        return Objects.equals(this.account.getId(), user.getAccount().getId());
//    }
}
