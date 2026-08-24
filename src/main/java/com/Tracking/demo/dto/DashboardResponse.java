package com.Tracking.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DashboardResponse {

    private Long totalAdmins;
    private Long totalTrainers;
    private Long totalStudents;
    private Long totalAssignments;
    private Long totalSubmissions;
    private Long evaluatedSubmissions;
    private Long pendingSubmissions;
}