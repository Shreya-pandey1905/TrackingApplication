package com.Tracking.demo.service;

import com.Tracking.demo.dto.SubmissionRequest;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.entity.SubmissionStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SubmissionService {
    Submission submitAssignment(Long assignmentId, Long studentId, SubmissionRequest request);
    Submission updateSubmission(Long submissionId,Long studentId,SubmissionRequest request);

    List<Submission> getStudentSubmissions(Long studentId);
    Submission updateSubmissionByAssignment(Long assignmentId, Long studentId, SubmissionRequest request);
    SubmissionStatus getSubmissionStatus(Long assignmentId, Long studentId);
    Submission getSubmissionResult(Long assignmentId, Long studentId);
    List<Submission> viewAllSubmissions();

}
