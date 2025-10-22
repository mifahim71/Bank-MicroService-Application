package com.auth.auth_service.service;

import com.auth.auth_service.dto.LoginRequestDto;
import com.auth.auth_service.dto.LoginResponseDto;
import com.auth.auth_service.dto.UserCreateRequestDto;
import com.auth.auth_service.dto.UserCreateResponseDto;
import com.auth.auth_service.entities.User;
import com.auth.auth_service.enums.Roles;
import com.auth.auth_service.repository.UserRepository;
import com.auth.auth_service.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserCreateResponseDto createUser(UserCreateRequestDto requestDto){

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .roles(Roles.USER)
                .build();

        User savedUser = userRepository.save(user);

        UserCreateResponseDto responseDto = new UserCreateResponseDto();

        responseDto.setEmail(savedUser.getEmail());
        responseDto.setCreatedAt(savedUser.getCreatedAt().toString());

        return responseDto;
    }

    public Optional<String> authenticate(LoginRequestDto loginRequestDto){

        return userRepository.findByEmail(loginRequestDto.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getId().toString(), user.getEmail(), user.getRoles().toString()));
    }

    public boolean validateToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }
}
