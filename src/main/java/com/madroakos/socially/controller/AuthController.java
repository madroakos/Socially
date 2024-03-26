package com.madroakos.socially.controller;

import com.madroakos.socially.config.JwtTokenUtil;
import com.madroakos.socially.dto.JwtRequest;
import com.madroakos.socially.dto.JwtResponse;
import com.madroakos.socially.dto.RegistrationRequest;
import com.madroakos.socially.model.TokenBlacklist;
import com.madroakos.socially.model.User;
import com.madroakos.socially.repository.TokenBlacklistRepository;
import com.madroakos.socially.service.CustomUserDetailsService;
import com.madroakos.socially.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            User user = userService.registerNewUser(registrationRequest);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        String jwtToken = token.substring(7);
        if (tokenBlacklistRepository.existsByToken(jwtToken)) {
            return ResponseEntity.badRequest().body("Token is blacklisted");
        }
        if (!jwtTokenUtil.validateToken(jwtToken)) {
            return ResponseEntity.badRequest().body("Token is invalid or has been tampered with");
        }
        if (jwtTokenUtil.isTokenExpired(jwtToken)) {
            return ResponseEntity.badRequest().body("Token is expired");
        }
        return ResponseEntity.ok().body("Token is valid");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        tokenBlacklistRepository.save(new TokenBlacklist(jwtToken));
        return ResponseEntity.ok("Logged out successfully");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

