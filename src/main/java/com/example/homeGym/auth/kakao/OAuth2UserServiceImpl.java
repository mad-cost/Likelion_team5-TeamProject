package com.example.homeGym.auth.kakao;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
// OAuth2UserServiceImpl이 성공적으로 OAuth2 과정을 마무리 했을 때,
// 넘겨받은 사용자 정보를 바탕으로 JWT를 생성,
// 클라이언트한테 JWT를 전달
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 어떤 서비스 제공자를 사용했는지
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();
        // TODO 서비스 제공자에 따라 데이터 처리를 달리 하고 싶을 때

        // OAuth2 제공자로 부터 받은 데이터를 원하는 방식으로 다시 정리하기 위한 Map
        Map<String, Object> attributes = new HashMap<>();
        String nameAttribute = "";

        // Kakao 아이디로 로그인
        if (registrationId.equals("kakao")) {
            log.info(oAuth2User.getAttributes().toString());
            // Kakao에서 받아온 정보다
            attributes.put("id",oAuth2User.getAttribute("id"));
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
            attributes.put("provider","kakao");
            attributes.put("name", kakaoAccount.get("name"));
            attributes.put("email", kakaoAccount.get("email"));
            attributes.put("gender",kakaoAccount.get("gender"));
            attributes.put("profile_image_url", kakaoProfile.get("profile_image_url"));
            attributes.put("birthday", kakaoAccount.get("birthday"));
            attributes.put("birthyear", kakaoAccount.get("birthyear"));
            nameAttribute = "email";
        }

        log.info(attributes.toString());
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                nameAttribute
        );
    }
}
