package org.example.user_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Exception.ResembleEmailFound;
import org.example.Exception.UsernameNotFound;
import org.example.config.JwtService;
import org.example.course.*;
import org.example.user_service.model.*;
import org.example.user_service.repository.UserRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CourseRepository courseRepository;
    private final CacheManager cacheManager;

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
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var userDetails = (UserDetails)auth.getPrincipal();

        String jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Cacheable(value ="admin", key="getAllUser" )
    public List<UserDetails> getAllUser() {
            List<User> users = userRepository.findAll();
            return new ArrayList<UserDetails>(users);
    }

    @Cacheable(value ="user", key="@jwtService.extractUsername(#token)" )
    public UserDTO getUser(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFound("User not found"));
        List<CourseDTO> courseDTOs = user.getCourses().stream().map(course -> CourseDTO.builder().id(course.getId()).name(course.getName()).duration(course.getDuration() + " minutes").build()).toList();

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .courses(courseDTOs)
                .role(user.getRole())
                .build();
    }

    @Transactional
    @CachePut(value ="course", key="#data.name")
    public CourseDTO addCourse(CourseRequest data) {
        Course course = Course.builder()
                .name(data.name())
                .duration(data.duration())
                .build();
        courseRepository.save(course);
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .duration(data.duration() + " minutes")
                .build();
    }

    @Transactional
    public CourseResponse enrollCourse(CourseEnrollRequest data) {
        User user = userRepository.findByEmail(data.email()).orElseThrow(() -> new UsernameNotFound("User not found"));
        List<CourseDTO> courseDTOs = user.getCourses().stream().map(course -> CourseDTO.builder().id(course.getId()).name(course.getName()).duration(course.getDuration() + " minutes").build()).toList();
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .courses(courseDTOs)
                .role(user.getRole())
                .build();
        Course courses = courseRepository.findById(data.courseId()).orElseThrow(() -> new UsernameNotFound("Course not found"));
        user.getCourses().add(courses);
        Cache cache = Objects.requireNonNull(cacheManager.getCache("user"));
        cache.put(data.email(), userDTO);
        return CourseResponse.builder()
                .courseId(courses.getId())
                .name(courses.getName())
                .description(courses.getDuration() + " minutes")
                .build();
    }
}
