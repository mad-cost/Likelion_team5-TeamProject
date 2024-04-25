package com.example.homeGym.auth.jwt;

import com.example.homeGym.auth.dto.CustomInstructorDetails;
import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.auth.service.InstructorDetailsManager;
import com.example.homeGym.auth.service.JpaUserDetailsManager;
import com.example.homeGym.auth.utils.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final InstructorDetailsManager instructorDetailsManager;

    private final CookieUtil cookieUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("try jwt filter");

        String jwtToken = cookieUtil.getCookie("Authorization", request);

        if(jwtToken != null) {
            if (jwtTokenUtils.validate(jwtToken)){

                SecurityContext context = SecurityContextHolder.createEmptyContext();

                String email = jwtTokenUtils.parseClaims(jwtToken).getSubject();
                String userType = jwtTokenUtils.parseClaims(jwtToken).get("userType", String.class);

                if(userType.equals("user")){
                    CustomUserDetails userDetails = (CustomUserDetails) jpaUserDetailsManager.loadUserByUsername(email);
                    for (GrantedAuthority authority : userDetails.getAuthorities()){
                        log.info("authorities: {}",authority.getAuthority());
                    }
                    // 인증 정보 생성
                    AbstractAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            jwtToken,
                            userDetails.getAuthorities()
                    );
                    // 인증 정보 등록
                    context.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(context);
                    log.info("set security context with jwt");
                }
                else {
                    CustomInstructorDetails instructorDetails = (CustomInstructorDetails) instructorDetailsManager.loadUserByUsername(email);
                    for (GrantedAuthority authority : instructorDetails.getAuthorities()){
                        log.info("authorities: {}",authority.getAuthority());
                    }
                    // 인증 정보 생성
                    AbstractAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            instructorDetails,
                            jwtToken,
                            instructorDetails.getAuthorities()
                    );
                    // 인증 정보 등록
                    context.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(context);
                    log.info("set security context with jwt");
                }
            }
            else {
                log.warn("jwt validation failed");
            }
        }

        filterChain.doFilter(request, response);
    }

}
