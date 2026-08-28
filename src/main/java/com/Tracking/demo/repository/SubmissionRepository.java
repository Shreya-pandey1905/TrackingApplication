package com.Tracking.demo.repository;

import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.entity.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Submission findByAssignmentIdAndStudentId(Long assignmentId,Long studentId);
    List<Submission> findByAssignmentId(Long assignmentId);

    List<Submission> findByStudentId(Long studentId);
    Long countByStatus(SubmissionStatus status);
}
