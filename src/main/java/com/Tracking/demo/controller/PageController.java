package com.Tracking.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Super Admin pages
    @GetMapping("/super-admin/dashboard")
    public String superAdminDashboard() {
        return "super-admin/dashboard";
    }

    @GetMapping("/super-admin/admins")
    public String superAdminAdmins() {
        return "super-admin/admins";
    }

    @GetMapping("/super-admin/trainers")
    public String superAdminTrainers() {
        return "super-admin/trainers";
    }

    @GetMapping("/super-admin/students")
    public String superAdminStudents() {
        return "super-admin/students";
    }

    @GetMapping("/super-admin/assignments")
    public String superAdminAssignments() {
        return "super-admin/assignments";
    }

    @GetMapping("/super-admin/submissions")
    public String superAdminSubmissions() {
        return "super-admin/submissions";
    }

    @GetMapping("/super-admin/profile")
    public String superAdminProfile() {
        return "super-admin/profile";
    }

    @GetMapping("/super-admin/reset-password")
    public String superAdminResetPassword() {
        return "super-admin/reset-password";
    }

    // Admin pages
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/trainers")
    public String adminTrainers() {
        return "admin/trainers";
    }

    @GetMapping("/admin/students")
    public String adminStudents() {
        return "admin/students";
    }

    @GetMapping("/admin/assignments")
    public String adminAssignments() {
        return "admin/assignments";
    }

    @GetMapping("/admin/submissions")
    public String adminSubmissions() {
        return "admin/submissions";
    }

    @GetMapping("/admin/profile")
    public String adminProfile() {
        return "admin/profile";
    }

    @GetMapping("/admin/reset-password")
    public String adminResetPassword() {
        return "admin/reset-password";
    }

    // Trainer pages
    @GetMapping("/trainer/dashboard")
    public String trainerDashboard() {
        return "trainer/dashboard";
    }

    @GetMapping("/trainer/assignments")
    public String trainerAssignments() {
        return "trainer/assignments";
    }

    @GetMapping("/trainer/students")
    public String trainerStudents() {
        return "trainer/students";
    }

    @GetMapping("/trainer/submissions")
    public String trainerSubmissions() {
        return "trainer/submissions";
    }

    @GetMapping("/trainer/profile")
    public String trainerProfile() {
        return "trainer/profile";
    }

    @GetMapping("/trainer/reset-password")
    public String trainerResetPassword() {
        return "trainer/reset-password";
    }

    // Student pages
    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "student/dashboard";
    }

    @GetMapping("/student/assignments")
    public String studentAssignments() {
        return "student/assignments";
    }

    @GetMapping("/student/submissions")
    public String studentSubmissions() {
        return "student/submissions";
    }

    @GetMapping("/student/profile")
    public String studentProfile() {
        return "student/profile";
    }

    @GetMapping("/student/reset-password")
    public String studentResetPassword() {
        return "student/reset-password";
    }
}
