package com.Tracking.demo.dto;

import com.Tracking.demo.entity.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AssignmentResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime assignedDate;
    private LocalDateTime dueDate;
    private Long maxMarks;
    private AssignmentStatus status;
    private Long trainerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}