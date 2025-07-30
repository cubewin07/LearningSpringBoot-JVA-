package org.example.course;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Builder
public class Course {

    @Id
    @SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "course_id_seq")
    private final Long id;
    private final String name;
    private final Long duration;

    @ManyToMany(mappedBy = "courses")
    private List<User> users = new ArrayList<>();

}
