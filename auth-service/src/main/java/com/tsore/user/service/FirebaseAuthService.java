package com.tsore.user.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.tsore.user.dto.FirebaseAuthRequest;
import com.tsore.user.dto.LoginResponse;
import com.tsore.user.entity.AuthProvider;
import com.tsore.user.entity.User;
import com.tsore.user.repository.UserRepository;
import com.tsore.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class FirebaseAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse authenticateWithFirebase(FirebaseAuthRequest request) throws Exception {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(request.getToken());
        String email = decodedToken.getEmail();

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user = existingUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setProvider(AuthProvider.GOOGLE);
            return userRepository.save(newUser);
        });

        String jwt = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(user.getEmail(), jwt);
    }
}
