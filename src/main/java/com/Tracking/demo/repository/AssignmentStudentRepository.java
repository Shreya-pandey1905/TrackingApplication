package com.Tracking.demo.repository;

import com.Tracking.demo.entity.AssignmentStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AssignmentStudentRepository extends JpaRepository<AssignmentStudent, Long> {
    List<AssignmentStudent> findByStudentId(Long studentId);
    List<AssignmentStudent> findByAssignmentId(Long assignmentId);

    AssignmentStudent findByAssignmentIdAndStudentId(Long assignmentId,Long studentId);

}
