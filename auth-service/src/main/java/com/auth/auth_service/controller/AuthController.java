package com.auth.auth_service.controller;

import com.auth.auth_service.dto.*;
import com.auth.auth_service.service.AuthService;
import com.auth.auth_service.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto requestDto){
        UserCreateResponseDto user = authService.createUser(requestDto);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto){

        Optional<String> token = authService.authenticate(loginRequestDto);

        if(token.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token.get());

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserDetailsDto> validateToken(@RequestHeader("Authorization") String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);

        if(!authService.validateToken(authHeader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserId(jwtUtil.getUserId(token));
        userDetailsDto.setEmail(jwtUtil.getEmail(token));
        userDetailsDto.setRoles(jwtUtil.getRoles(token));


        return ResponseEntity.ok().body(userDetailsDto);
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> heathCheck(){
        return ResponseEntity.ok().body("health-check");
    }
}
