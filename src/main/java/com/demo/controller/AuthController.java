package com.demo.controller;

import com.demo.config.CustomUserDetailsService;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.payload.LoginDto;
import com.demo.payload.SignupDto;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;

    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        return ResponseEntity.ok("User signed in successfully!!");
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registration(@Valid @RequestBody SignupDto signupDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Additional logic if needed

            // Invalidate session and clear authentication
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();

            // Clear cookies
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.ok("Logout successfully");
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
        }
        return new ResponseEntity<>("Logout successfully", HttpStatus.OK);
    }
}
