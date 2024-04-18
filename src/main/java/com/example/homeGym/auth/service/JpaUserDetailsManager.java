package com.example.homeGym.auth.service;

import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;

    public JpaUserDetailsManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        if(!userExists("admin@gmail.com")) {
            createUser(CustomUserDetails.builder()
                    .name("admin")
                    .password(passwordEncoder.encode("admin"))
                    .profileImageUrl("")
                    .gender("none")
                    .email("admin@gmail.com")
                    .birthyear("2000")
                    .birthday("1113")
                    .roles("ROLE_ADMIN")
                    .state("USER")
                    .build());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(email);

        User userEntity = optionalUser.get();

        return CustomUserDetails.builder()
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .profileImageUrl(userEntity.getProfileImageUrl())
                .gender(userEntity.getGender())
                .email(userEntity.getEmail())
                .birthyear(userEntity.getBirthyear())
                .birthday(userEntity.getBirthday())
                .roles(userEntity.getRoles())
                .state(String.valueOf(userEntity.getState()))
                .build();
    }


    @Override
    public void createUser(UserDetails user) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) user;

            User newUser = User.builder()
                    .name(userDetails.getName())
                    .password(userDetails.getPassword())
                    .profileImageUrl(userDetails.getProfileImageUrl())
                    .gender(userDetails.getGender())
                    .email(userDetails.getEmail())
                    .birthyear(userDetails.getBirthyear())
                    .birthday(userDetails.getBirthday())
                    .roles(userDetails.getRoles())
                    .state(User.UserState.valueOf(userDetails.getState()))
                    .build();

            userRepository.save(newUser);

        } catch (ClassCastException e) {
            log.error("Failed Cast to: {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void deleteUser(String username) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }


}
