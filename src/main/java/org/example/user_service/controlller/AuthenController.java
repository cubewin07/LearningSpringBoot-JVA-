package org.example.user_service.controlller;

import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.Exception.TooManyRequest;
import org.example.course.CourseDTO;
import org.example.course.CourseEnrollRequest;
import org.example.course.CourseRequest;
import org.example.course.CourseResponse;
import org.example.rate_limiting.RateLimiterService;
import org.example.user_service.model.AuthenticationRequest;
import org.example.user_service.model.AuthenticationResponse;
import org.example.user_service.model.RegisterRequest;
import org.example.user_service.model.UserDTO;
import org.example.user_service.service.AuthenService;
import org.springframework.data.domain.*;
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

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDetails>> getAllUser(@RequestParam(name = "number", defaultValue = "10") int size, @RequestParam(name = "page", defaultValue = "0") int page) {
        Bucket bucket = rateLimiterService.resolveBucket("admin");
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(authenService.getAllUser(pageable));
    }

    @GetMapping("/own")
    public ResponseEntity<UserDTO> getUserData(@RequestHeader("Authorization") String BearerToken) {
        Bucket bucket = rateLimiterService.resolveBucket(BearerToken);
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        String token = BearerToken.substring(7);
        return ResponseEntity.ok(authenService.getUser(token));
    }

    @PostMapping("/course")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseRequest data) {
        Bucket bucket = rateLimiterService.resolveBucket(data.name());
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        return ResponseEntity.ok(authenService.addCourse(data));
    }

    @PostMapping("/enrollCourse")
    public ResponseEntity<CourseResponse> enrollCourse(@RequestBody CourseEnrollRequest data) {
        Bucket bucket = rateLimiterService.resolveBucket(data.email());
        if(!bucket.tryConsume(1)) {
            throw new TooManyRequest("You many request sent, please try again after a minute");
        }
        return ResponseEntity.ok(authenService.enrollCourse(data));
    }
}
