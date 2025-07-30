package org.example.course;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "course_id_seq")
    private Long id;
    private String name;
    private Long duration;

    @ManyToMany(mappedBy = "courses")
    private List<User> users;

}
