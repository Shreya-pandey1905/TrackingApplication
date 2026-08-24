create database trackingApp;
use trackingApp;
show tables;

select * from user;
select * from assignment;
select * from assignment_student;
select * from submission;
truncate table assignment;	
INSERT INTO user
    (name, email, phone, role, active, created_at, updated_at)
VALUES
    ('Jake Gyllenhaal', 'jake@gmail.com', '9876543210', 'SUPER_ADMIN', true, '2026-08-01 10:00:00', '2026-08-01 10:00:00'),
    ('Alice Smith', 'alice.smith@gmail.com', '9876543211', 'STUDENT', true, '2026-08-02 11:30:00', '2026-08-02 11:30:00'),
    ('Bob Johnson', 'bob.johnson@gmail.com', '9876543212', 'TRAINER', true, '2026-08-03 09:15:00', '2026-08-03 09:15:00'),
    ('Sarah Wilson', 'sarah.wilson@gmail.com', '9876543213', 'ADMIN', true, '2026-08-04 14:20:00', '2026-08-05 08:10:00'),
    ('Mike Brown', 'mike.brown@gmail.com', '9876543214', 'ADMIN', true, '2026-08-05 16:45:00', '2026-08-05 16:45:00');

INSERT INTO assignment
    (title, description, assigned_date, due_date, max_marks, status, trainer_id, created_at, updated_at)
VALUES
    ('Java Basics',
     'Complete basic Java programming exercises',
     '2026-08-01 10:00:00',
     '2026-08-10 23:59:59',
     100,
     'CREATED',
     3,
     '2026-08-01 10:00:00',
     '2026-08-01 10:00:00'),

    ('Spring Boot REST API',
     'Build a REST API using Spring Boot',
     '2026-08-03 09:30:00',
     '2026-08-15 23:59:59',
     100,
     'ASSIGNED',
     3,
     '2026-08-03 09:30:00',
     '2026-08-05 11:00:00'),

    ('Database Design',
     'Design tables and relationships for a student management system',
     '2026-08-05 11:00:00',
     '2026-08-18 23:59:59',
     50,
     'CREATED',
     3,
     '2026-08-05 11:00:00',
     '2026-08-05 11:00:00'),

    ('JPA and Hibernate',
     'Create entity relationships using JPA and Hibernate',
     '2026-08-07 14:00:00',
     '2026-08-20 23:59:59',
     75,
     'ASSIGNED',
     3,
     '2026-08-07 14:00:00',
     '2026-08-08 10:30:00'),

    ('Authentication API',
     'Implement JWT authentication and authorization',
     '2026-08-10 09:00:00',
     '2026-08-25 23:59:59',
     100,
     'CLOSED',
     3,
     '2026-08-10 09:00:00',
     '2026-08-22 16:00:00');
     
     INSERT INTO assignment_student
    (assignment_id, student_id, assigned_at)
VALUES
    (1, 2, '2026-08-02 10:00:00'),
    (2, 2, '2026-08-04 11:30:00'),
    (3, 2, '2026-08-06 09:15:00'),
    (4, 2, '2026-08-08 14:00:00'),
    (5, 2, '2026-08-11 10:45:00');
    
    INSERT INTO submission
    (assignment_id, student_id, submission_text, submitted_at, marks, feedback, status, evaluated_at)
VALUES
    (1, 2,
     'Completed the Java basics exercises including OOP and collections.',
     '2026-08-08 15:30:00',
     85,
     'Good understanding of Java fundamentals.',
     'EVALUATED',
     '2026-08-09 10:00:00'),

    (2, 2,
     'Implemented the Spring Boot REST API with CRUD operations.',
     '2026-08-14 18:20:00',
     90,
     'Excellent REST API implementation.',
     'EVALUATED',
     '2026-08-15 11:30:00'),

    (3, 2,
     'Created the database design with tables and relationships.',
     '2026-08-17 14:45:00',
     42,
     'Database design is mostly correct.',
     'EVALUATED',
     '2026-08-18 09:30:00'),

    (4, 2,
     'Implemented JPA entities and Hibernate relationships.',
     '2026-08-19 16:10:00',
     NULL,
     NULL,
     'SUBMITTED',
     NULL),

    (5, 2,
     NULL,
     NULL,
     NULL,
     NULL,
     'PENDING',
     NULL);

SELECT * FROM submission WHERE id = 4;

SELECT * FROM assignment WHERE id = 5;

SELECT * FROM assignment_student
;
