package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.JwtReq;
import com.fa20se28.vma.response.JwtRes;
import com.fa20se28.vma.service.UserService;
import com.fa20se28.vma.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthenticateController(AuthenticationManager authenticationManager,
                                  UserService userService,
                                  JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtRes> createAuthenticationToken(@RequestBody JwtReq jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUserId(), jwtRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserId());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtRes(jwt));
    }
}
