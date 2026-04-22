package com.ericksilva.notificationapi.auth;

import com.ericksilva.notificationapi.auth.dto.AuthRequest;
import com.ericksilva.notificationapi.auth.dto.AuthResponse;
import com.ericksilva.notificationapi.user.User;
import com.ericksilva.notificationapi.user.UserPrincipal;
import com.ericksilva.notificationapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(
        @RequestBody AuthRequest request
    ) {
        User user = userService.register(
                request.getEmail(),
                request.getPassword()
        );
        String token = jwtService.generateToken(user.getId(),user.getEmail());
        return ResponseEntity.status(201).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request
    ) {
        UserPrincipal user;
        try {
            user = userService.loadUserByUsername(request.getEmail());
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(401).build();
        }

        if (!encoder.matches(request.getPassword(),user.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtService.generateToken(user.getId(),user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
