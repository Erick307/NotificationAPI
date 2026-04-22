package com.ericksilva.notificationapi.user;

import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User register(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User newUser = User
                .builder()
                .email(email)
                .password(encoder.encode(password))
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public UserPrincipal loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
