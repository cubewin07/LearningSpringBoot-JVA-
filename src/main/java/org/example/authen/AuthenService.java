package org.example.authen;

import lombok.RequiredArgsConstructor;
import org.example.Exception.ResembleEmailFound;
import org.example.Exception.UsernameNotFound;
import org.example.config.JwtService;
import org.example.user.Role;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new ResembleEmailFound("Email is already taken");
        }
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticateUser( AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFound("User not found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<UserDetails> getAllUser() {
            List<User> users = userRepository.findAll();
            return new ArrayList<UserDetails>(users);
    }

    public UserDetails getUser(String token) {
        String username = jwtService.extractUsername(token);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFound("User not found"));
    }
}
