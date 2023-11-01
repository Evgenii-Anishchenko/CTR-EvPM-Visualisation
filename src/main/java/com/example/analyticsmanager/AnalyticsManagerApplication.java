package com.example.analyticsmanager;

import com.example.analyticsmanager.service.AnalyticsService;
import com.example.analyticsmanager.service.MmDmaAggregationService;
import com.example.analyticsmanager.service.SiteAggregationService;
import com.example.analyticsmanager.service.ViewEventService;
import com.example.analyticsmanager.service.VisitEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;

@Slf4j
@SpringBootApplication
public class AnalyticsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDataOnStartup(VisitEventService visitEventService
        , ViewEventService viewEventService
        , AnalyticsService analyticsService
        , JdbcTemplate jdbcTemplate
        , MmDmaAggregationService mmDmaAggregationService
        , SiteAggregationService siteAggregationService) {
        return args -> {
            //Load data from CSV files on startup
//            File eventTypeCsvFile = new File("src/main/resources/interview.data/interview.y.csv");
//            visitEventService.saveRecordsFromCSV(eventTypeCsvFile);
//            File viewEventCsvFile = new File("src/main/resources/interview.data/interview.X.csv");
//            viewEventService.saveRecordsFromCSV(viewEventCsvFile);
            log.info("Data loaded from CSV files");
            jdbcTemplate.execute("refresh materialized view concurrently public.joined_data;");
            log.info("Materialized view refreshed");
            // Calculate and save chart data
            analyticsService.calculateAndSaveChartData();
            log.info("Chart data saved");
            // Calculate and save aggregation data
            siteAggregationService.calculateAndSaveAggregation();
            log.info("Site aggregation data saved");
            mmDmaAggregationService.calculateAndSaveAggregation();
            log.info("Mm_dma aggregation data saved");
        };
    }
}
