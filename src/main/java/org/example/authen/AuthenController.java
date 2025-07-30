package org.example.authen;

import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.Exception.TooManyRequest;
import org.example.course.CourseDTO;
import org.example.course.CourseRequest;
import org.example.rate_limiting.RateLimiterService;
import org.example.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthenController {

    private final AuthenService authenService;
    private final RateLimiterService rateLimiterService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>  registerUser(@Valid @RequestBody RegisterRequest request) {
        Bucket bucket = rateLimiterService.resolveBucket(request.email());
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        return ResponseEntity.ok(authenService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest request) {
        Bucket bucket = rateLimiterService.resolveBucket(request.email());
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        return ResponseEntity.ok(authenService.authenticateUser(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetails>> getAllUser() {
        return ResponseEntity.ok(authenService.getAllUser());
    }

    @GetMapping("/own")
    public ResponseEntity<User> getUserData(@RequestHeader("Authorization") String BearerToken) {
        Bucket bucket = rateLimiterService.resolveBucket(BearerToken);
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        String token = BearerToken.substring(7);
        return ResponseEntity.ok(authenService.getUser(token));
    }

    @PostMapping("/course")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseRequest data) {
        return ResponseEntity.ok(authenService.addCourse(data));
    }

    @PostMapping("/enrollCourse")
    public ResponseEntity<CourseResponse> enrollCourse(@RequestBody CourseEnrollRequest data) {
        return ResponseEntity.ok(authenService.enrollCourse(data));
    }
}
