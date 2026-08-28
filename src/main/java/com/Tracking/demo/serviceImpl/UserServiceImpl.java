package com.Tracking.demo.serviceImpl;
import com.Tracking.demo.customJwt.JwtService;
import com.Tracking.demo.dto.*;
import com.Tracking.demo.entity.*;
import com.Tracking.demo.exception.DuplicateResourceException;
import com.Tracking.demo.exception.ResourceNotFoundException;
import com.Tracking.demo.repository.AssignmentRepository;
import com.Tracking.demo.repository.AssignmentStudentRepository;
import com.Tracking.demo.repository.SubmissionRepository;
import com.Tracking.demo.repository.UserRepository;
import com.Tracking.demo.service.MailService;
import com.Tracking.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentStudentRepository assignmentStudentRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    @Override
    public UserResponse createAdmin(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists....");
        }
          String password = request.getName() + "123";
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ADMIN);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        mailService.sendPasswordMail(savedUser.getEmail(), password);
        return modelMapper.map(savedUser, UserResponse.class);
    }
    @Override
    public List<UserResponse> getAllAdmins() {
        List<User> users = userRepository.findByRole(Role.ADMIN);
        List<UserResponse> responseList = new ArrayList<>();
        for (User user : users) {
            UserResponse response = modelMapper.map(user, UserResponse.class);
            responseList.add(response);
        }
        return responseList;
    }
    @Override
    public UserResponse getAdminById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("Admin not found");
        }
        return modelMapper.map(user, UserResponse.class);
    }
    @Override
    public UserResponse updateAdmin(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("only admin can make changes");
        }
        modelMapper.map(request, user);
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }
    @Override
    public void deleteAdmin(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
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
        for (User user : users) {
            UserResponse response = modelMapper.map(user, UserResponse.class);
            responseList.add(response);
        }
        return responseList;
    }
    @Override
    public List<UserResponse> getAllStudents() {
        List<User> users = userRepository.findByRole(Role.STUDENT);
        List<UserResponse> responseList = new ArrayList<>();
        for (User user : users) {
            UserResponse response = modelMapper.map(user, UserResponse.class);
            responseList.add(response);
        }
        return responseList;
    }
    @Override
    public UserResponse createTrainer(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        String password = request.getName() + "123";
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.TRAINER);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        mailService.sendPasswordMail(savedUser.getEmail(), password);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse createStudent(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        String password = request.getName() + "123";
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.STUDENT);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        mailService.sendPasswordMail(savedUser.getEmail(), password);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse updateTrainerOrStudent(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRole() != Role.TRAINER && user.getRole() != Role.STUDENT) {
            throw new ResourceNotFoundException("Only trainer or student can be updated");
        }
        modelMapper.map(request, user);
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void assignAssignmentToStudents(Long assignmentId, List<Long> studentIds) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        for (Long studentId : studentIds) {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
            if (student.getRole() != Role.STUDENT) {
                throw new ResourceNotFoundException("Student not found");
            }
            AssignmentStudent existing = assignmentStudentRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
            if (existing == null) {
                AssignmentStudent assignmentStudent = new AssignmentStudent();
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
        return submissionRepository.findByAssignmentId(assignmentId);
    }
    @Override
    public Submission evaluateSubmission(Long submissionId, EvaluationRequest request) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        submission.setMarks(request.getMarks());
        submission.setFeedback(request.getFeedback());
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

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                            request.getEmail(),
                            request.getPassword()
                    ));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Credentials");
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LoginResponseDto responseDto=modelMapper.map(user, LoginResponseDto.class);
        responseDto.setToken(jwtService.generateToken(user.getEmail(),user.getRole().name()));
        return responseDto;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        mailService.sendPasswordMail(user.getEmail(),newPassword);

    }


}
