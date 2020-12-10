package com.fa20se28.vma.controller;

import com.fa20se28.vma.configuration.JwtUtil;
import com.fa20se28.vma.request.JwtReq;
import com.fa20se28.vma.response.JwtRes;
import com.fa20se28.vma.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateController {
    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    public AuthenticateController(
            UserServiceImpl userService,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtRes> createAuthenticationToken(@RequestBody JwtReq jwtRequest) {
        UserDetails userDetails = userService.loadUserByPhoneNumberAndPassword(jwtRequest);
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtRes(jwt));
    }
}
