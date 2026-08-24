package com.Tracking.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime assignedDate ;
    private LocalDateTime dueDate;
    private Long maxMarks;
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;
//    //todo: onr to many
//    private Long trainerId;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
