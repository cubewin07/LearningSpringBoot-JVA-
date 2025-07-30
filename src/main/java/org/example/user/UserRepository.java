package org.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.courses where u.email = :email")
    Optional<User> findByEmailWithCourses(@Param("email") String email);
    Boolean existsByEmail(String email);
}
