package com.example.homeGym.auth.dto;

import com.example.homeGym.user.entity.User;
import jakarta.persistence.metamodel.Metamodel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    @Setter
    private String name;
    @Setter
    private String profileImageUrl;
    @Setter
    private String gender;
    @Setter
    private String email;
    @Setter
    private String birthday;
    @Setter
    private String roles;

    @Getter
    private User entity;

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
        userDetails.setProfileImageUrl(entity.getProfileImageUrl());
        userDetails.setGender(entity.getGender());
        userDetails.setEmail(entity.getEmail());
        userDetails.setBirthday(entity.getBirthday());
        return userDetails;
    }

    @Override
    public String getPassword() {return null;} //카카오라 없다?

    @Override
    public String getUsername() {return this.entity.getName();}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
}
