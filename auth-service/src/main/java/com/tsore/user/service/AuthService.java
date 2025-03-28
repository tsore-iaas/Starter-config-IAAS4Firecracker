package com.tsore.user.service;


import com.tsore.user.dto.LoginRequest;
import com.tsore.user.dto.LoginResponse;
import com.tsore.user.entity.AuthProvider;
import com.tsore.user.entity.User;
import com.tsore.user.repository.UserRepository;
import com.tsore.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (user.getProvider() == AuthProvider.LOCAL &&
                !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(user.getEmail(), token);
    }

    public LoginResponse signup(LoginRequest request) {
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new RuntimeException("Utilisateur avec cet email existe déjà");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setProvider(AuthProvider.LOCAL);
        String token = jwtUtil.generateToken(newUser.getEmail());
        User savedUser = userRepository.save(newUser);

        return new LoginResponse(savedUser.getEmail(), token);
    }

    public Long getUserIdFromToken(String token) {
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return user.getId();
    }
}
