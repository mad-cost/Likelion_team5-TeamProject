package com.example.homeGym.auth.dto;

import com.example.homeGym.user.entity.User;
import jakarta.persistence.metamodel.Metamodel;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String profileImageUrl;
    private String gender;
    private String birthyear;
    private String birthday;
    private String roles;
    private String state;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String[] rawAuthorities = roles.split(",");
        for (String rawAuthority : rawAuthorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(rawAuthority));
        }
        return grantedAuthorities;
    }

    public static CustomUserDetails fromEntity(User entity) {
        CustomUserDetails userDetails = new CustomUserDetails();

        userDetails.setName(entity.getName());
        userDetails.setPassword(entity.getPassword());
        userDetails.setProfileImageUrl(entity.getProfileImageUrl());
        userDetails.setGender(entity.getGender());
        userDetails.setEmail(entity.getEmail());
        userDetails.setBirthyear(entity.getBirthyear());
        userDetails.setBirthday(entity.getBirthday());
        userDetails.setRoles(entity.getRoles());
        userDetails.setState(String.valueOf(entity.getState()));
        return userDetails;
    }


    @Override
    public String getPassword() {return this.password;}

    @Override
    public String getUsername() {return this.email;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}

}
