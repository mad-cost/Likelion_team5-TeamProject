package com.example.homeGym.user.dto;

import com.example.homeGym.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String profileImageUrl;
    private String gender;
    private String email;
    private String birthyear;
    private String birthday;
    private String roles = "ROLE_USER";
    private User.UserState state;
    private LocalDateTime createdAt;

    public static UserDto fromEntity(User entity){
        UserDto.UserDtoBuilder builder =UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .profileImageUrl(entity.getProfileImageUrl())
                .gender(entity.getGender())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .birthyear(entity.getBirthyear())
                .roles(entity.getRoles())
                .state(entity.getState())
                .createdAt(entity.getCreatedAt());
        return builder.build();
    }

}
