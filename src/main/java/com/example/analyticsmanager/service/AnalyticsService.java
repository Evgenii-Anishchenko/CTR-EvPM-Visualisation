package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.ChartUnitEntity;
import com.example.analyticsmanager.entity.ChartUnitEntity.TimeInterval;
import com.example.analyticsmanager.repo.ChartUnitRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ChartUnitRepo chartUnitRepo;
    private final JdbcTemplate jdbcTemplate;

    public List<ChartUnitEntity> getChartData(String resolution, String tag) {
        TimeInterval timeInterval = TimeInterval.valueOf(resolution.toUpperCase());
        return getListOfChartUnitsByIntervalAndTag(timeInterval, tag);
    }

    public void calculateAndSaveChartData() {
        for (TimeInterval interval : TimeInterval.values()) {
            for (String tag : getClickTags()) {
                List<ChartUnitEntity> chartUnits = getListOfChartUnitsByIntervalAndTag(interval, tag);
                log.info("Saving {} chart units for tag {} and interval {}", chartUnits.size(), tag, interval);
                chartUnitRepo.saveAll(chartUnits);
            }
        }
    }

    private List<String> getClickTags() {
        String sql = "SELECT DISTINCT visit_tag FROM public.joined_data jd " +
            "WHERE jd.visit_tag NOT LIKE 'v%'" +
            "AND jd.visit_tag IS NOT NULL";
        log.info("Getting click tags");
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<ChartUnitEntity> getListOfChartUnitsByIntervalAndTag(TimeInterval timeInterval, String tag) {
        String vTag = "v" + tag;
        String timeIntervalString = timeInterval.toString();
        String sql = "SELECT " +
            "    DATE_TRUNC(?, view_reg_time) AS time_scale, " +
            "    COUNT(*) AS total_views, " +
            "    SUM(CASE WHEN visit_tag = ? THEN 1 ELSE 0 END) AS clicks, " +
            "    (SUM(CASE WHEN visit_tag = ? THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS ctr, " +
            "    (SUM(CASE WHEN (visit_tag = ? or visit_tag = ?) THEN 1 ELSE 0 END) * 1000.0 / COUNT(*)) AS evpm " +
            "FROM " +
            "    public.joined_data " +
            "GROUP BY " +
            "    time_scale " +
            "ORDER BY " +
            "    time_scale;";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, timeIntervalString, tag, tag, tag, vTag);
        log.info("Getting chart units for tag {} and interval {}", tag, timeInterval);
        return results.stream()
            .map(result -> {
                ChartUnitEntity chartUnit = new ChartUnitEntity();
                chartUnit.setViews((long) result.get("total_views"));
                chartUnit.setClicks((long) result.get("clicks"));
                chartUnit.setTimestamp((Date) result.get("time_scale"));
                chartUnit.setCtr((BigDecimal) result.get("ctr"));
                chartUnit.setEvpm((BigDecimal) result.get("evpm"));
                chartUnit.setTag(tag);
                chartUnit.setTimeInterval(timeInterval);

                return chartUnit;
            })
            .toList();
    }
}
