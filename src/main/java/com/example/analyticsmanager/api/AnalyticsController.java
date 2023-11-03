package com.example.analyticsmanager.api;

import com.example.analyticsmanager.entity.ChartUnitEntity;
import com.example.analyticsmanager.entity.MmDmaAggrEntity;
import com.example.analyticsmanager.entity.SiteAggrEntity;
import com.example.analyticsmanager.service.AnalyticsService;
import com.example.analyticsmanager.service.MmDmaAggregationService;
import com.example.analyticsmanager.service.SiteAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final MmDmaAggregationService mmDmaAggregationService;
    private final SiteAggregationService siteAggregationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chart")
    public ResponseEntity<List<ChartUnitEntity>> getChart(
        @RequestParam String resolution,
        @RequestParam String tag
    ) {
        return ResponseEntity.ok(analyticsService.getChartData(resolution, tag));
    }

    @GetMapping("/mm-dma-aggr")
    public ResponseEntity<Page<MmDmaAggrEntity>> getMmDmaAggr(
        @RequestParam String tag,
        Pageable pageable
    ) {
        Page<MmDmaAggrEntity> page = mmDmaAggregationService.getAggregation(tag, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/site-aggr")
    public ResponseEntity<Page<SiteAggrEntity>> getSiteAggr(
        @RequestParam String tag,
        Pageable pageable
    ) {
        Page<SiteAggrEntity> page = siteAggregationService.getAggregation(tag, pageable);
        return ResponseEntity.ok(page);
    }
}
