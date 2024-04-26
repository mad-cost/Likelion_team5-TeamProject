package com.example.homeGym.mail;

import com.example.homeGym.mail.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final MailService mailService;

//    @GetMapping("/{email_addr}/authcode")
//    public ResponseEntity<String> sendEmailPath(
//            @PathVariable
//            String email_addr
//    )throws MessagingException{
//        mailService.sendEmail(email_addr);
//        return ResponseEntity.ok("이메일을 확인하세요");
//    }
    @PostMapping("/send")
    public ResponseEntity<?> sendEmailPath(
            @RequestBody
            Map<String, String> request
    )throws MessagingException{
        String email = request.get("email");
        mailService.sendEmail(email);
        return ResponseEntity.ok(Map.of("isSend", true));
    }

    @PostMapping("/check")
    public ResponseEntity<?> sendEmailAndCode(
            @RequestBody
            Map<String, String> request
    ){
        System.out.println(request.get("code"));
        if (mailService.verifyEmailCode(request.get("email"), request.get("code"))){
            return ResponseEntity.ok(Map.of("isCheck", true));
        }
        return ResponseEntity.notFound().build();
    }


//    @PostMapping("/{email_addr}/authcode")
//    public ResponseEntity<String> sendEmailAndCode(
//            @PathVariable
//            String email_addr,
//            @RequestBody
//            EmailRequestDto dto
//    ){
//        System.out.println(dto.getCode());
//        if (mailService.verifyEmailCode(email_addr, dto.getCode())){
//            return ResponseEntity.ok("done");
//        }
//        return ResponseEntity.notFound().build();
//    }
}
