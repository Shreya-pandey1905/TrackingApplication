package com.Tracking.demo.controller;

import com.Tracking.demo.dto.UserRequest;
import com.Tracking.demo.dto.UserResponse;
import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.response.ApiResponse;
import com.Tracking.demo.service.AssignmentService;
import com.Tracking.demo.service.SubmissionService;
import com.Tracking.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
public class SuperAdminController {

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    SubmissionService submissionService;

    @PostMapping("/createAdmin")
    public ResponseEntity<ApiResponse<UserResponse>> createAdmin(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createAdmin(request);
        ApiResponse<UserResponse> apiResponse =ApiResponse.<UserResponse>builder()
                        .success(true)
                        .msg("Admin created successfully....")
                        .data(response)
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/viewAllAdmins")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllAdmins() {
        List<UserResponse> admins = userService.getAllAdmins();
        ApiResponse<List<UserResponse>> apiResponse = ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .msg("Admins retrieved successfully")
                        .data(admins)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("/getAdminById/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getAdminById( @PathVariable Long id) {
        UserResponse admin = userService.getAdminById(id);
        ApiResponse<UserResponse> apiResponse =ApiResponse.<UserResponse>builder()
                        .success(true)
                        .msg("Admin retrieved successfully")
                        .data(admin)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @PutMapping("/updateAdminById/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        UserResponse admin = userService.updateAdmin(id, request);
        ApiResponse<UserResponse> apiResponse=ApiResponse.<UserResponse>builder()
                        .success(true)
                        .msg("Admin updated successfully")
                        .data(admin)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @DeleteMapping("/deleteAdminById/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long id) {
        userService.deleteAdmin(id);
        ApiResponse<Void> apiResponse =
                ApiResponse.<Void>builder()
                        .success(true)
                        .msg("Admin deactivated successfully")
                        .data(null)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/getAllTrainers")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllTrainers() {
        List<UserResponse> trainers = userService.getAllTrainers();
        ApiResponse<List<UserResponse>> apiResponse=ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .msg("Trainers retrieved successfully")
                        .data(trainers)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("/getAllStudents")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllStudents() {
        List<UserResponse> students = userService.getAllStudents();
        ApiResponse<List<UserResponse>> apiResponse = ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .msg("Students retrieved successfully")
                        .data(students)
                        .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/getAllAssignments")
    public ResponseEntity<ApiResponse<List<Assignment>>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        ApiResponse<List<Assignment>> apiResponse = ApiResponse.<List<Assignment>>builder()
                .success(true)
                .msg("Assignments retrieved successfully")
                .data(assignments)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/viewAllSubmissions")
    public ResponseEntity<ApiResponse<List<Submission>>> viewAllSubmissions() {
        List<Submission> submissions = submissionService.viewAllSubmissions();
        ApiResponse<List<Submission>> apiResponse=ApiResponse.<List<Submission>>builder()
                .success(true)
                .msg("Submissions retrieved successfully")
                .data(submissions)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}