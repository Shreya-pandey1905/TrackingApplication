package com.Tracking.demo.controller;

import com.Tracking.demo.dto.*;
import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.AssignmentStatus;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.entity.SubmissionStatus;
import com.Tracking.demo.response.ApiResponse;
import com.Tracking.demo.service.AssignmentService;
import com.Tracking.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @PutMapping("/updateAssignment/{id}")
    public ResponseEntity<ApiResponse<Assignment>> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentRequest request) {
        Assignment assignment = assignmentService.updateAssignment(id, request);
        ApiResponse<Assignment> apiResponse = ApiResponse.<Assignment>builder()
                .success(true)
                .msg("Assignment updated successfully")
                .data(assignment)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/createAssignments")
    public ResponseEntity<ApiResponse<AssignmentResponse>> createAssignment(@Valid @RequestBody AssignmentRequest request) {
        AssignmentResponse assignment = assignmentService.createAssignment(request);
        ApiResponse<AssignmentResponse> apiResponse =ApiResponse.<AssignmentResponse>builder()
                .success(true)
                .msg("Assignment created successfully")
                .data(assignment)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/getAssignedAndCreatedAssignments")
    public ResponseEntity<ApiResponse<List<Assignment>>> getActiveAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        List<Assignment> filteredAssignments = new ArrayList<>();
        for (Assignment assignment : assignments) {
            if (assignment.getStatus() == AssignmentStatus.CREATED || assignment.getStatus() == AssignmentStatus.ASSIGNED) {
                filteredAssignments.add(assignment);
            }
        }
        ApiResponse<List<Assignment>> apiResponse =
                ApiResponse.<List<Assignment>>builder()
                        .success(true)
                        .msg("Assigned and created assignments retrieved successfully")
                        .data(filteredAssignments)
                        .build();

        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/assignAssignment/{assignmentId}")
    public ResponseEntity<ApiResponse<String>> assignAssignmentToStudents(
            @PathVariable Long assignmentId,
            @RequestBody List<Long> studentIds) {

        userService.assignAssignmentToStudents(assignmentId,studentIds);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .msg("Assignment assigned to students successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllstudents")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllStudents() {
        List<UserResponse> students = userService.getAllStudents();
        ApiResponse<List<UserResponse>> apiResponse=ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .msg("Students retrieved successfully")
                .data(students)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/getStudentSubmissions/{assignmentId}")
    public ResponseEntity<ApiResponse<List<Submission>>> getStudentSubmissions(
            @PathVariable Long assignmentId) {
        List<Submission> submissions =userService.getStudentSubmissions(assignmentId);
        ApiResponse<List<Submission>> response = ApiResponse.<List<Submission>>builder()
                        .success(true)
                        .msg("Student submissions retrieved successfully")
                        .data(submissions)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/evaluateSubmission/{submissionId}")
    public ResponseEntity<ApiResponse<Submission>> evaluateSubmission(
            @PathVariable Long submissionId,
            @Valid @RequestBody EvaluationRequest request) {
        Submission submission = userService.evaluateSubmission(submissionId, request);
        ApiResponse<Submission> response = ApiResponse.<Submission>builder()
                .success(true)
                .msg("Submission evaluated successfully")
                .data(submission)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/changeSubmissionStatus/{submissionId}")
    public ResponseEntity<ApiResponse<Submission>> changeSubmissionStatus(
            @PathVariable Long submissionId,
            @RequestParam SubmissionStatus status) {
        Submission submission = userService.changeSubmissionStatus(submissionId,status);
        ApiResponse<Submission> response = ApiResponse.<Submission>builder()
                        .success(true)
                        .msg("Submission status changed successfully")
                        .data(submission)
                        .build();

        return ResponseEntity.ok(response);
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
