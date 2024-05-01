package com.example.homeGym.auth.Controller;

import com.example.homeGym.auth.dto.SignInDto;
import com.example.homeGym.auth.utils.CookieUtil;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final InstructorService instructorService;

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

    @PostMapping("/instructor/signin")
    public ResponseEntity<String> login(HttpServletResponse res, @RequestBody SignInDto signInDto) throws Exception {

        try{
            boolean login = instructorService.signIn(res, signInDto.getEmail(), signInDto.getPassword());
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
