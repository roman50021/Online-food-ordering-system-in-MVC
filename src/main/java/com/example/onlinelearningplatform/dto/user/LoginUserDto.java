package com.example.onlinelearningplatform.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}

