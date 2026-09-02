package com.Tracking.demo.service;

import com.Tracking.demo.dto.DashboardResponse;
import org.springframework.stereotype.Service;

@Service

public interface DashboardService {
    DashboardResponse getDashboard();
}
