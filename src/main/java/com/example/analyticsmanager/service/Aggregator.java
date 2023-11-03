package com.example.analyticsmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class Aggregator {

    private final JdbcTemplate jdbcTemplate;

    public List<String> getClickTags() {
        String sql = "SELECT DISTINCT visit_tag FROM public.joined_data jd " +
            "WHERE jd.visit_tag NOT LIKE 'v%' AND jd.visit_tag IS NOT NULL";
        log.info("Getting click tags");
        return jdbcTemplate.queryForList(sql, String.class);
    }

    protected abstract void calculateAndSaveAggregation();

    protected abstract Page<?> getAggregation(String tag, Pageable pageable);
}
