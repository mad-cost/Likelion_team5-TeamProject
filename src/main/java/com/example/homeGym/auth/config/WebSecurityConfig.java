package com.example.homeGym.auth.config;

import com.example.homeGym.auth.jwt.JwtTokenFilter;
import com.example.homeGym.auth.jwt.JwtTokenUtils;
import com.example.homeGym.auth.kakao.OAuth2SuccessHandler;
import com.example.homeGym.auth.kakao.OAuth2UserServiceImpl;
import com.example.homeGym.auth.service.InstructorDetailsManager;
import com.example.homeGym.auth.service.JpaUserDetailsManager;
import com.example.homeGym.auth.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenUtils jwtTokenUtils;
    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final InstructorDetailsManager instructorDetailsManager;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final CookieUtil cookieUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                // csrf  보안 헤제
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/main", "/error",
                                "/admin/signin",
                                "/user/loginpage",
                                "/program/**",
                                "/auth/admin/signin",
                                "/instructor/signin",
                                "/instructor/proposal/**",
                                "/instructor/check-email",
                                "/auth/instructor/signin",
                                "/css/**","/js/**",
                                "/static/**",
                                "/email/**",
                                "/main", "/main/match","/main/match/search",
                                "/main/introduce/program/{programId}",
                                "/main/introduce/program/all/{programId}",
                                "/instructor/address/**",
                                "/toss"
                        )
                        .permitAll()

                        .requestMatchers(
                                "/user/review/**",
                                "/user/mypage",
                                "/user/program/**",
                                "/orders/**",
                                "/user/zzim",
                                "/main/introduce/program/{programId}/apply"

                        )
                        .hasRole("USER") // 유저 권한을 가진 사용자만 접근 가능

                        .requestMatchers(
                              "/instructor/accept/**",

                                "/instructor/review/**",
                                "/instructor/withdraw",
                                "/instructor",
                                "/instructor/profile",
                                "/instructor/update-result",
                                "/instructor/program/**",
                                "/instructor/settlement/**",
                                "/program/**",
                                "/instructor/schedule/**"

                        )
                        .hasRole("INSTRUCTOR") // 강사 권한을 가진 사용자만 접근 가능

                        .requestMatchers(
                                "/admin",
                                "/admin/instructor",
                                "/admin/user/**",
                                "/admin/program/**",
                                "/admin/settlement/**"

                        )
                        .hasRole("ADMIN") // 관리자 권한을 가진 사용자만 접근 가능

                        .requestMatchers(
                                "/user/signup",
                                "/user/signin",
                                "/instructor/signup"
                        )
                        .anonymous()
                        .anyRequest()
                        .authenticated()

                )

                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/user/loginpage")
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(
                        new JwtTokenFilter(
                                jwtTokenUtils,
                                jpaUserDetailsManager,
                                instructorDetailsManager,
                                cookieUtil
                        ),
                        AuthorizationFilter.class
                )

                .logout(logout -> logout
                        .logoutUrl("/auth/signout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/main") // 로그아웃 성공 후 리다이렉트될 URL
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("Authorization") // 로그아웃 시 삭제할 쿠키 이름
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

}
