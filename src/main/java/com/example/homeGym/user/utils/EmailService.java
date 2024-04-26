package com.example.homeGym.user.utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    private static final String title = "비밀번호 테스트 입니다";
    private static final String message = "안녕하세요 지금 테스트 중입니다. " +
            "+\n" +
            "회원님의 인증번호는 입니다";
    private static final String fromAddress = "kgukid38@gmail.com";

    public void sendMail(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("kgukid38@naver.com");
        mailMessage.setSubject(title);
        mailMessage.setText(message);
        mailMessage.setFrom(fromAddress);
        mailMessage.setReplyTo(fromAddress);

        mailSender.send(mailMessage);
    }
}
