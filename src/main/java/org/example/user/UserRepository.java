package org.example.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "courses")
    Optional<User> findByEmail(String email);


//    @EntityGraph(attributePaths = "courses")
//    Optional<User> findByEmailWithCourses(@Param("email") String email);
    Boolean existsByEmail(String email);
}
