package com.example.onlinelearningplatform.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotBlank(message = "Not null firstname")
    private String firstname;
    @NotBlank(message = "Not null lastname")
    private String lastname;
    @NotBlank(message = "Not null email")
    @Email
    private String email;
    @NotBlank(message = "Not null password")
    private String password;
}