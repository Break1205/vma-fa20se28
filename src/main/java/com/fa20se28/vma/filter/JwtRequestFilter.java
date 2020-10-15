package com.fa20se28.vma.filter;

import com.fa20se28.vma.model.UserAccount;
import com.fa20se28.vma.util.FirebaseTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter implements Filter {
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private FirebaseTokenUtil firebaseTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        System.out.println("authorizationHeader: " + authorizationHeader);
        String userId = null;
        String jwtToken = null;

        // get the token from Header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            System.out.println("jwtToken: " + jwtToken);

            userId = firebaseTokenUtil.getUserIdFromToken(jwtToken);
            System.out.println("userId" + userId);
        }

        // validate token
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserAccount userAccount = (UserAccount) jwtUserDetailsService.loadUserByUsername(userId);
            System.out.println("userAccount.getName()" + userAccount.getUsername());
            // if token is valid then configs Spring Security to set Authentication
            if (userAccount != null && userId.equals(userAccount.getUsername())) {
                System.out.println("Success");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userAccount.getUsername(), userAccount.getPassword(), userAccount.getAuthorities()
                        );
                System.out.println("Success2");
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("Success3");
            }
        }

        filterChain.doFilter(request, response);
    }
}