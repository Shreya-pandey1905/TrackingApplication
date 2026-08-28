package com.Tracking.demo.controller;

import com.Tracking.demo.dto.*;
import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.repository.UserRepository;
import com.Tracking.demo.service.AssignmentService;
import com.Tracking.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Tracking.demo.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @PostMapping("/createTrainer")
    public ResponseEntity<ApiResponse<UserResponse>> createTrainer(@Valid @RequestBody UserRequest request) {
   UserResponse trainer = userService.createTrainer(request);
        ApiResponse<UserResponse> apiResponse=ApiResponse.<UserResponse>builder()
                        .success(true)
                        .msg("Trainer created successfully")
                        .data(trainer)
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @PostMapping("/createStudent")
    public ResponseEntity<ApiResponse<UserResponse>> createStudent(@Valid @RequestBody UserRequest request) {
        UserResponse student = userService.createStudent(request);
        ApiResponse<UserResponse> apiResponse=ApiResponse.<UserResponse>builder()
                        .success(true)
                        .msg("Student created successfully")
                        .data(student)
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/updateTrainerOrStudentById/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateTrainerOrStudent(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateTrainerOrStudent(id, request);
        ApiResponse<UserResponse> apiResponse=ApiResponse.<UserResponse>builder()
                .success(true)
                        .msg("Trainer or student updated successfully")
                        .data(user)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/getAlltrainers")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllTrainers() {
        List<UserResponse> trainers = userService.getAllTrainers();
        ApiResponse<List<UserResponse>> apiResponse=ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .msg("Trainers retrieved successfully")
                        .data(trainers)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
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

    @GetMapping("/getAssignmentDetailsById/{id}")
    public ResponseEntity<ApiResponse<Assignment>> getAssignmentById(@PathVariable Long id) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        ApiResponse<Assignment> apiResponse =ApiResponse.<Assignment>builder()
                        .success(true)
                        .msg("Assignment found successfully")
                        .data(assignment)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/closeAssignment/{id}")
    public ResponseEntity<ApiResponse<Assignment>> deleteAssignment(
            @PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        ApiResponse<Assignment> apiResponse = ApiResponse.<Assignment>builder()
                .success(true)
                .msg("Assignment closed successfully")
                .data(null)
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
