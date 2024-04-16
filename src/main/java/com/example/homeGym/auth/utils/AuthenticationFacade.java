package com.example.homeGym.auth.utils;


import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User extractUser() {
        CustomUserDetails userDetails = (CustomUserDetails) getAuth().getPrincipal();
        return userDetails.getEntity();
    }


}
