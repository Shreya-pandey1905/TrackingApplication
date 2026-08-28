package com.Tracking.demo.repository;

import com.Tracking.demo.entity.Role;
import com.Tracking.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findByRole(Role role);
    Optional<User> findByEmail (String email);
    Long countByRole(Role role);
}
