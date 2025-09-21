create table users_courses (
    users_id BIGINT NOT NULL,
    courses_id BIGINT NOT NULL,
    PRIMARY KEY (users_id, courses_id),
    FOREIGN KEY (users_id) REFERENCES users(id),
    FOREIGN KEY (courses_id) REFERENCES courses(id)
)