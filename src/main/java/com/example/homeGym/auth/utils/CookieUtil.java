package com.example.homeGym.auth.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {




    private final int COOKIE_EXPIRATION = 14 * 24 * 60 * 60;

    public void createCookie(HttpServletResponse res, String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRATION);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }

    public String getCookie(String key, HttpServletRequest req){
        final Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(key))
                    return cookie.getValue();
            }
        }
        return null;
    }

    public Cookie deleteCookie(String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        return cookie;
    }

}
