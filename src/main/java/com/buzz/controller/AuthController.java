package com.buzz.controller;

import com.buzz.config.JwtProvider;
import com.buzz.exception.UserException;
import com.buzz.model.User;
import com.buzz.repository.UserRepository;
import com.buzz.request.LoginRequest;
import com.buzz.response.AuthResponse;
import com.buzz.service.CustomUserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImplementation customUserServiceImplementation;

    public AuthController(UserRepository userRepository,  JwtProvider jwtProvider, PasswordEncoder passwordEncoder, CustomUserServiceImplementation customUserServiceImplementation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserServiceImplementation = customUserServiceImplementation;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("Email is already linked with another account.");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success!");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, "Login Success!");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
