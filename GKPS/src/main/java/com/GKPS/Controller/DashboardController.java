package com.GKPS.Controller;

import com.GKPS.DTO.Response.DashboardResponse;
import com.GKPS.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getDashboardData() {
        return dashboardService.getDashboardData();
    }
}
