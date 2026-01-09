package com.sci.archive.controller;

import com.sci.archive.dto.JwtResponse;
import com.sci.archive.dto.LoginRequest;
import com.sci.archive.dto.MessageResponse;
import com.sci.archive.dto.SignupRequest;
import com.sci.archive.model.User;
import com.sci.archive.repository.UserRepository;
import com.sci.archive.security.JwtUtils;
import com.sci.archive.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFullName(),
                role.replace("ROLE_", "")
        ));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        
        // Create new user account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFullName(signUpRequest.getFullName());
        
        String roleStr = signUpRequest.getRole().toUpperCase();
        
        try {
            User.Role role = User.Role.valueOf(roleStr);
            user.setRole(role);
            
            // Validate and set ID based on role
            if (role == User.Role.STUDENT) {
                if (signUpRequest.getStudentId() == null || signUpRequest.getStudentId().isEmpty()) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Student ID is required for students!"));
                }
                user.setStudentId(signUpRequest.getStudentId());
            } else if (role == User.Role.LECTURER) {
                if (signUpRequest.getStaffId() == null || signUpRequest.getStaffId().isEmpty()) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Staff ID is required for lecturers!"));
                }
                user.setStaffId(signUpRequest.getStaffId());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid role specified!"));
        }
        
        user.setActive(true);
        userRepository.save(user);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
