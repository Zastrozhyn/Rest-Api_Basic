package com.epam.esm.controller;

import com.epam.esm.assembler.UserModelAssembler;
import com.epam.esm.dto.AuthenticationDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.model.UserModel;
import com.epam.esm.security.SecurityUser;
import com.epam.esm.service.UserService;
import com.epam.esm.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserModelAssembler assembler;

    @Autowired
    public AuthController(JWTUtil jwtUtil, UserService userService,
                          AuthenticationManager authenticationManager, UserModelAssembler assembler) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.assembler = assembler;
    }

    @PostMapping("/registration")
    public UserModel create(@RequestBody User user){
        return assembler.toModelWithLinks(userService.create(user));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getName(),
                        authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new EntityException(ExceptionCode.BAD_CREDENTIALS.getErrorCode());
        }
        String token = jwtUtil.generateToken(authenticationDTO.getName());
        return Map.of("jwt-token", token);
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return user.getAuthorities().toString();
    }
}
