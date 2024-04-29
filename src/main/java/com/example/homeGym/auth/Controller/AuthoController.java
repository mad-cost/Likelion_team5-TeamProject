package com.example.homeGym.auth.Controller;

import com.example.homeGym.auth.dto.SignInDto;
import com.example.homeGym.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthoController {

    private final UserService userService;

    @PostMapping("/admin/signin")
    public ResponseEntity<String> signUpAdmin(HttpServletResponse res, @RequestBody SignInDto signInDto){

        try{
            boolean login = userService.signinAdmin(res, signInDto.getEmail(), signInDto.getPassword());
            if(login){
             return ResponseEntity.ok("success");
            } else{
                return ResponseEntity.status(401).body(null);
            }
        }catch(Exception e){

            return ResponseEntity.internalServerError().body(null);

        }
    }
}
