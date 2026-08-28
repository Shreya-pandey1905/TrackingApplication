package com.Tracking.demo.service;

import com.Tracking.demo.dto.*;
import com.Tracking.demo.entity.Submission;
import com.Tracking.demo.entity.SubmissionStatus;
import com.Tracking.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    //super admin

    UserResponse createAdmin(UserRequest request);
    List<UserResponse> getAllAdmins();
    UserResponse getAdminById(Long id);
    UserResponse updateAdmin(Long id, UserRequest request);
    void deleteAdmin(Long id);
    List<UserResponse> getAllTrainers();
    List<UserResponse> getAllStudents();
    //admin
    UserResponse createTrainer(UserRequest request);
    UserResponse createStudent(UserRequest request);
    UserResponse updateTrainerOrStudent(Long id, UserRequest request);
    User getUserById(Long id);
    void assignAssignmentToStudents(Long assignmentId,List<Long> studentIds);
    List<Submission> getStudentSubmissions(Long assignmentId);
    Submission evaluateSubmission(Long submissionId, EvaluationRequest request);
    Submission changeSubmissionStatus(Long submissionId, SubmissionStatus status);
    LoginResponseDto login(LoginRequestDto request);

    void resetPassword(String email, String newPassword);

}
