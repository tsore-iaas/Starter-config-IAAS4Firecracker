package com.tsore.user.controller;

import com.tsore.user.dto.FirebaseAuthRequest;
import com.tsore.user.dto.LoginRequest;
import com.tsore.user.dto.LoginResponse;
import com.tsore.user.service.AuthService;
import com.tsore.user.service.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @GetMapping("/hello")
    public String hello (){
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse>  signup(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/firebase")
    public ResponseEntity<LoginResponse>   firebaseLogin(@RequestBody FirebaseAuthRequest request) throws Exception {
        return ResponseEntity.ok(firebaseAuthService.authenticateWithFirebase(request));
    }

    @GetMapping("/get_user_id")
    public ResponseEntity<Long> getUserIdFromToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        Long userId = authService.getUserIdFromToken(token);

        return ResponseEntity.ok(userId);
    }
}