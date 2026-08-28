package com.Tracking.demo.controller;

import com.Tracking.demo.dto.*;
import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.entity.SubmissionStatus;
import com.Tracking.demo.entity.User;
import com.Tracking.demo.response.ApiResponse;
import com.Tracking.demo.service.AssignmentService;
import com.Tracking.demo.service.SubmissionService;
import com.Tracking.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    UserService userService;
    @Autowired
    AssignmentService assignmentService;
    @Autowired
    SubmissionService submissionService;


    @GetMapping("getMyAssignments/{studentId}/assignments")
    public ResponseEntity<ApiResponse<List<Assignment>>> getMyAssignments(@PathVariable Long studentId) {
        List<Assignment> assignments = assignmentService.getAssignmentsForStudent(studentId);
        ApiResponse<List<Assignment>> apiResponse =
                ApiResponse.<List<Assignment>>builder()
                        .success(true)
                        .msg("Student assignments retrieved successfully")
                        .data(assignments)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getAssignmentDetails/{studentId}/assignments/{assignmentId}")
    public ResponseEntity<ApiResponse<Assignment>> getAssignmentDetails(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.getAssignmentForStudent(assignmentId, studentId);
        ApiResponse<Assignment> apiResponse =
                ApiResponse.<Assignment>builder()
                        .success(true)
                        .msg("Assignment details retrieved successfully")
                        .data(assignment)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("submitAssignment/{studentId}/assignments/{assignmentId}/submissions")
    public ResponseEntity<ApiResponse<Submission>> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @Valid @RequestBody SubmissionRequest request) {
        Submission submission = submissionService.submitAssignment(assignmentId, studentId, request);
        ApiResponse<Submission> apiResponse =
                ApiResponse.<Submission>builder()
                        .success(true)
                        .msg("Assignment submitted successfully")
                        .data(submission)
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("updateSubmission/{studentId}/assignments/{assignmentId}/submission")
    public ResponseEntity<ApiResponse<Submission>> updateSubmission(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @Valid @RequestBody SubmissionRequest request) {
        Submission submission = submissionService.updateSubmissionByAssignment(
                assignmentId, studentId, request);
        ApiResponse<Submission> apiResponse =
                ApiResponse.<Submission>builder()
                        .success(true)
                        .msg("Submission updated successfully")
                        .data(submission)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getAllMySubmissions/{studentId}/submissions")
    public ResponseEntity<ApiResponse<List<Submission>>> getAllMySubmissions(
            @PathVariable Long studentId) {
        List<Submission> submissions = submissionService.getStudentSubmissions(studentId);
        ApiResponse<List<Submission>> apiResponse =
                ApiResponse.<List<Submission>>builder()
                        .success(true)
                        .msg("Student submissions retrieved successfully")
                        .data(submissions)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getSubmissionStatus/{studentId}/assignments/{assignmentId}/submission/status")
    public ResponseEntity<ApiResponse<SubmissionStatus>> getSubmissionStatus(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId) {
        SubmissionStatus status = submissionService.getSubmissionStatus(assignmentId, studentId);
        ApiResponse<SubmissionStatus> apiResponse =
                ApiResponse.<SubmissionStatus>builder()
                        .success(true)
                        .msg("Submission status retrieved successfully")
                        .data(status)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getSubmissionResult/{studentId}/assignments/{assignmentId}/result")
    public ResponseEntity<ApiResponse<Submission>> getSubmissionResult(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId) {
        Submission submission = submissionService.getSubmissionResult(assignmentId, studentId);
        ApiResponse<Submission> apiResponse =
                ApiResponse.<Submission>builder()
                        .success(true)
                        .msg("Submission result retrieved successfully")
                        .data(submission)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestBody ResetPasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        userService.resetPassword(userDetails.getUsername(),request.getNewPassword());
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .msg("Password reset successfully done")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = userService.login(request);
        ApiResponse<LoginResponseDto> apiResponse =
                ApiResponse.<LoginResponseDto>builder()
                        .success(true)
                        .msg("Login successful..")
                        .data(response)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }




}