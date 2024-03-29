package com.example.mimimetrtestjob.dto;


import com.example.mimimetrtestjob.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;


    public static User to(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getUsername())
                .build();
    }
}
