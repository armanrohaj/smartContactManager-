package com.durgesh.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "username is required")
    @Size(min = 3, message = "minimum 3 characters are required")
    private String name;
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "minimum 6 characters are required")
    private String password;
    @Size(min=8, max=12, message = "Invalid number")
    private String phoneNumber;
    @NotBlank(message = "about is required")
    private String about;
}
