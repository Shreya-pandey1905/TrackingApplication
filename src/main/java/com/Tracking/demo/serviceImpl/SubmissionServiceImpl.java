package com.Tracking.demo.serviceImpl;

import com.Tracking.demo.dto.SubmissionRequest;
import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.AssignmentStudent;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.entity.SubmissionStatus;
import com.Tracking.demo.exception.DuplicateResourceException;
import com.Tracking.demo.exception.ResourceNotFoundException;
import com.Tracking.demo.repository.AssignmentRepository;
import com.Tracking.demo.repository.AssignmentStudentRepository;
import com.Tracking.demo.repository.SubmissionRepository;
import com.Tracking.demo.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    AssignmentStudentRepository assignmentStudentRepository;

    @Override
    public Submission submitAssignment(Long assignmentId, Long studentId, SubmissionRequest request) {
        Assignment assignment=assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new ResourceNotFoundException("Assignment not found"));
        AssignmentStudent assignmentStudent= assignmentStudentRepository.findByAssignmentIdAndStudentId(assignmentId,studentId);
        if(assignmentStudent==null){
            throw new ResourceNotFoundException("Assignment is not assigned to this student");
        }
        Submission oldSubmission=submissionRepository.findByAssignmentIdAndStudentId(assignmentId,studentId);
        if(oldSubmission!=null){
            throw new DuplicateResourceException("Assignment already submitted");
        }
        if(LocalDateTime.now().isAfter(assignment.getDueDate())){
            throw new RuntimeException("Submission deadline has passed");
        }
        Submission submission=new Submission();
        submission.setAssignmentId(assignmentId);
        submission.setStudentId(studentId);
        submission.setSubmissionText(request.getSubmissionText());
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setStatus(SubmissionStatus.SUBMITTED);
        return submissionRepository.save(submission);
    }

    @Override
    public Submission updateSubmission(Long submissionId, Long studentId, SubmissionRequest request) {
        Submission submission=submissionRepository.findById(submissionId)
                .orElseThrow(()->new ResourceNotFoundException("Submission not found"));

        if(!submission.getStudentId().equals(studentId)){
            throw new RuntimeException("You cannot update another student's submission");
        }
        Assignment assignment=assignmentRepository.findById(submission.getAssignmentId())
                .orElseThrow(()->new ResourceNotFoundException("Assignment not found"));
        if(LocalDateTime.now().isAfter(assignment.getDueDate())){
            throw new RuntimeException("Submission deadline has passed");
        }
        if(submission.getStatus().equals(SubmissionStatus.EVALUATED)){
            throw new RuntimeException("Evaluated submission cannot be updated");
        }
        submission.setSubmissionText(request.getSubmissionText());
        submission.setSubmittedAt(LocalDateTime.now());
        return submissionRepository.save(submission);
    }


    @Override
    public List<Submission> getStudentSubmissions(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }


    @Override
    public Submission updateSubmissionByAssignment(Long assignmentId, Long studentId, SubmissionRequest request) {
        Submission submission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
        if (submission == null) {
            throw new ResourceNotFoundException("Submission not found");
        }
        return updateSubmission(submission.getId(), studentId, request);
    }

    @Override
    public SubmissionStatus getSubmissionStatus(Long assignmentId, Long studentId) {
        Submission submission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
        if (submission == null) {
            throw new ResourceNotFoundException("Submission not found");
        }
        return submission.getStatus();    }

    @Override
    public Submission getSubmissionResult(Long assignmentId, Long studentId) {
                   Submission submission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
            if (submission == null) {
                throw new ResourceNotFoundException("Submission not found");
            }
            return submission;
        }

    @Override
    public List<Submission> viewAllSubmissions() {
        return submissionRepository.findAll();
    }

}
