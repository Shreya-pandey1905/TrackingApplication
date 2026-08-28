package com.Tracking.demo.serviceImpl;
import com.Tracking.demo.dto.AssignmentRequest;
import com.Tracking.demo.dto.AssignmentResponse;
import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.AssignmentStatus;
import com.Tracking.demo.entity.AssignmentStudent;
import com.Tracking.demo.exception.ResourceNotFoundException;
import com.Tracking.demo.repository.AssignmentRepository;
import com.Tracking.demo.repository.AssignmentStudentRepository;
import com.Tracking.demo.repository.UserRepository;
import com.Tracking.demo.service.AssignmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    AssignmentStudentRepository assignmentStudentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public AssignmentResponse createAssignment(AssignmentRequest request) {
        Assignment assignment = modelMapper.map(request, Assignment.class);
        assignment.setTrainer(userRepository.findById(request.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found")));
        assignment.setStatus(AssignmentStatus.CREATED);
        assignment.setCreatedAt(LocalDateTime.now());
        assignment.setUpdatedAt(LocalDateTime.now());
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return modelMapper.map(savedAssignment, AssignmentResponse.class);
    }
    @Override
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment updateAssignment(Long id, AssignmentRequest request) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        modelMapper.map(request, assignment);
        assignment.setTrainer(userRepository.findById(request.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found")));
        assignment.setUpdatedAt(LocalDateTime.now());
        return assignmentRepository.save(assignment);
    }

    @Override
    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        assignment.setStatus(AssignmentStatus.CLOSED);
        assignment.setUpdatedAt(LocalDateTime.now());
        assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsForStudent(Long studentId) {
        List<AssignmentStudent> assignmentStudents =
                assignmentStudentRepository.findByStudentId(studentId);
        List<Assignment> assignments = new ArrayList<>();
        for (AssignmentStudent assignmentStudent : assignmentStudents) {
            assignments.add(assignmentStudent.getAssignment());
        }
        return assignments;
    }

    @Override
    public Assignment getAssignmentForStudent(Long assignmentId, Long studentId) {
        AssignmentStudent assignmentStudent =
                assignmentStudentRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
        if (assignmentStudent == null) {
            throw new ResourceNotFoundException("Assignment is not assigned to this student");
        }
        return assignmentStudent.getAssignment();
    }
}
