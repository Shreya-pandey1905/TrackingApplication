package com.Tracking.demo.service;

import com.Tracking.demo.dto.AssignmentRequest;
import com.Tracking.demo.dto.AssignmentResponse;
import com.Tracking.demo.entity.Assignment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface AssignmentService {
    AssignmentResponse createAssignment(AssignmentRequest request);
    Assignment getAssignmentById(Long id);
    List<AssignmentResponse> getAllAssignments();
    Assignment updateAssignment(Long id, AssignmentRequest request);
    void deleteAssignment(Long id);

    List<Assignment> getAssignmentsForStudent(Long studentId);
    Assignment getAssignmentForStudent(Long assignmentId,Long studentId);
}
