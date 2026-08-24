package com.Tracking.demo.serviceImpl;

import com.Tracking.demo.dto.UserRequest;
import com.Tracking.demo.dto.UserResponse;
import com.Tracking.demo.entity.*;
import com.Tracking.demo.exception.DuplicateResourceException;
import com.Tracking.demo.exception.ResourceNotFoundException;
import com.Tracking.demo.repository.AssignmentRepository;
import com.Tracking.demo.repository.AssignmentStudentRepository;
import com.Tracking.demo.repository.SubmissionRepository;
import com.Tracking.demo.repository.UserRepository;
import com.Tracking.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class UserServiceImpl implements UserService {


    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentStudentRepository assignmentStudentRepository;

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponse createAdmin(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists....");
        }
       User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(Role.ADMIN);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllAdmins() {
        List<User> users = userRepository.findByRole(Role.ADMIN);
        List<UserResponse> responseList = new ArrayList<>();
        for (User user : users) {
            responseList.add(mapToResponse(user));
        }
        return responseList;
    }

    @Override
    public UserResponse getAdminById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Admin not found"));
        if (user.getRole()!=Role.ADMIN) {
            throw new ResourceNotFoundException("Admin not found");
        }
        return mapToResponse(user);
    }

    @Override
    public UserResponse updateAdmin(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("Admin not found");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUpdatedAt(LocalDateTime.now());
        User updateduser=userRepository.save(user);
        return mapToResponse(updateduser);
    }

    @Override
    public void deleteAdmin(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Admin not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("Admin not found");
        }
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllTrainers() {
        List<User> users = userRepository.findByRole(Role.TRAINER);
        List<UserResponse> responseList = new ArrayList<>();
        for (User user:users) {
            responseList.add(mapToResponse(user));
        }
        return responseList;
    }

    @Override
    public List<UserResponse> getAllStudents() {
        List<User> users = userRepository.findByRole(Role.STUDENT);
        List<UserResponse> responseList = new ArrayList<>();
        for (User user:users) {
            responseList.add(mapToResponse(user));
        }
        return responseList;    }

    @Override
    public UserResponse createTrainer(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(Role.TRAINER);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse createStudent(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(Role.STUDENT);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse updateTrainerOrStudent(Long id, UserRequest request) {
                 User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            if (user.getRole() != Role.TRAINER && user.getRole() != Role.STUDENT) {
                throw new ResourceNotFoundException("Only trainer or student can be updated");
            }
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setUpdatedAt(LocalDateTime.now());
            User updatedUser = userRepository.save(user);
            return mapToResponse(updatedUser);
       }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void assignAssignmentToStudents(Long assignmentId,List<Long> studentIds) {
        Assignment assignment=assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new ResourceNotFoundException("Assignment not found"));
        for(Long studentId:studentIds){
            User student=userRepository.findById(studentId)
                    .orElseThrow(()->new ResourceNotFoundException("Student not found"));
            if(student.getRole()!=Role.STUDENT){
                throw new ResourceNotFoundException("Student not found");
            }
            AssignmentStudent existing=assignmentStudentRepository.findByAssignmentIdAndStudentId(assignmentId,studentId);

            if(existing==null){
                AssignmentStudent assignmentStudent=new AssignmentStudent();
                assignmentStudent.setAssignment(assignment);
                assignmentStudent.setStudent(student);
                assignmentStudent.setAssignedAt(LocalDateTime.now());
                assignmentStudentRepository.save(assignmentStudent);
            }
        }
        assignment.setStatus(AssignmentStatus.ASSIGNED);
        assignmentRepository.save(assignment);
    }

    @Override
    public List<Submission> getStudentSubmissions(Long assignmentId) {
        assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        return submissionRepository.findByAssignmentId(assignmentId);    }

    @Override
    public Submission evaluateSubmission(Long submissionId, Long marks, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        submission.setMarks(marks);
        submission.setFeedback(feedback);
        submission.setStatus(SubmissionStatus.EVALUATED);
        submission.setEvaluatedAt(LocalDateTime.now());
        return submissionRepository.save(submission);
    }

    @Override
    public Submission changeSubmissionStatus(Long submissionId, SubmissionStatus status) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
        submission.setStatus(status);
        return submissionRepository.save(submission);
    }


    // Entity → Response DTO
    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setActive(user.getActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
