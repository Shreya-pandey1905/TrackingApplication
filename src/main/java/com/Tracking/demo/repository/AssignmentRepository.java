package com.Tracking.demo.repository;

import com.Tracking.demo.entity.Assignment;
import com.Tracking.demo.entity.AssignmentStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
