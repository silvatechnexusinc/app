package com.sci.archive.dto;

import com.sci.archive.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @NotBlank
    @Size(max = 100)
    private String fullName;
    
    @NotBlank
    private String role; // STUDENT or LECTURER
    
    private String studentId; // Required if role is STUDENT
    
    private String staffId; // Required if role is LECTURER
}
