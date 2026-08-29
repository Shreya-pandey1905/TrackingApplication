package com.Tracking.demo.serviceImpl;

import com.Tracking.demo.dto.DashboardResponse;
import com.Tracking.demo.entity.Role;
import com.Tracking.demo.entity.SubmissionStatus;
import com.Tracking.demo.repository.AssignmentRepository;
import com.Tracking.demo.repository.SubmissionRepository;
import com.Tracking.demo.repository.UserRepository;
import com.Tracking.demo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;

    @Override
    public DashboardResponse getDashboard() {

        Long totalAdmins = userRepository.countByRole(Role.ADMIN);
        Long totalTrainers = userRepository.countByRole(Role.TRAINER);
        Long totalStudents = userRepository.countByRole(Role.STUDENT);

        Long totalAssignments = assignmentRepository.count();
        Long totalSubmissions = submissionRepository.count();
        Long evaluatedSubmissions =submissionRepository.countByStatus(SubmissionStatus.EVALUATED);
        Long pendingSubmissions =submissionRepository.countByStatus(SubmissionStatus.PENDING);

        return DashboardResponse.builder()
                .totalAdmins(totalAdmins)
                .totalTrainers(totalTrainers)
                .totalStudents(totalStudents)
                .totalAssignments(totalAssignments)
                .totalSubmissions(totalSubmissions)
                .evaluatedSubmissions(evaluatedSubmissions)
                .pendingSubmissions(pendingSubmissions)
                .build();
    }
}
