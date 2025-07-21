package org.example.authen;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthenController {

    private final AuthenService authenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>  registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenService.authenticateUser(request));
    }

}
