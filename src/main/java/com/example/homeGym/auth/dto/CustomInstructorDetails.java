package com.example.homeGym.auth.dto;

import com.example.homeGym.instructor.entity.Instructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
public class CustomInstructorDetails implements UserDetails {
    private Instructor instructor;

    private Long id;
    private String name;
    private String loginId;
    private String password;
    private String gender;
    private String birthyear;
    private String birthday;
    private String roles;
    private String state;
    private String career;
    private String profileImageUrl;
    private String certificate;
    private String medal;
    private String email;
    private String phone;
    private String bank;
    private String bankName;
    private Double rating;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority(instructor.getRoles()));
    }

    @Override
    public String getPassword() {
        return instructor.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
        return true;
    }



}
