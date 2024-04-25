package com.example.homeGym.auth.kakao;

import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.auth.jwt.JwtTokenUtils;
import com.example.homeGym.auth.service.JpaUserDetailsManager;
import com.example.homeGym.auth.utils.CookieUtil;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenUtils tokenUtils;
    private final UserRepository userRepository;
    private final JpaUserDetailsManager userDetailsManager;
    private final CookieUtil cookieUtil;
    //kakao chat http://pf.kakao.com/_nMxoeG
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2UserServiceImpl의 반환값이 할당된다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


        // 넘겨받은 정보를 바탕으로 사용자 정보를 준비
        String birthyear = oAuth2User.getAttribute("birthyear");
        String birthday = oAuth2User.getAttribute("birthday");
        String gender = oAuth2User.getAttribute("gender");
        String profileImageUrl = oAuth2User.getAttribute("profile_image_url");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        // 처음으로 이 소셜 로그인으로 로그인을 시도했다.
        if (!userRepository.existsByEmail(email)){
            // 새 계정을 만들어야 한다.
            userDetailsManager.createUser(CustomUserDetails.builder()
                    .name(name)
                    .password("")
                    .profileImageUrl(profileImageUrl)
                    .gender(gender)
                    .email(email)
                    .birthyear(birthyear)
                    .birthday(birthday)
                    .roles("ROLE_USER")
                    .state("USER")
                    .build());
        }


        CustomUserDetails details = (CustomUserDetails) userDetailsManager.loadUserByUsername(email);

        String jwt = tokenUtils.generateToken(details);
        cookieUtil.createCookie(response, "Authorization", jwt);

        String targetUrl = String.format(
                "http://localhost:8080/user/main"
        );
        getRedirectStrategy().sendRedirect(request, response, targetUrl);


    }
}
