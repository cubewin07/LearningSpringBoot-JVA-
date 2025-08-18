package org.example.user_service.repository;

import org.example.user_service.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "courses")
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "courses")
    <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);

//    @EntityGraph(attributePaths = "courses")
//    Optional<User> findByEmailWithCourses(@Param("email") String email);
    Boolean existsByEmail(String email);
}
