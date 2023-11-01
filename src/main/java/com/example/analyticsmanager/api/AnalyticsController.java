package com.example.analyticsmanager.api;

import com.example.analyticsmanager.entity.ChartUnitEntity;
import com.example.analyticsmanager.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chart")
    public ResponseEntity<List<ChartUnitEntity>> getChart(
        @RequestParam String resolution,
        @RequestParam String tag
    ) {
        return ResponseEntity.ok(analyticsService.getChartData(resolution, tag));
    }
}