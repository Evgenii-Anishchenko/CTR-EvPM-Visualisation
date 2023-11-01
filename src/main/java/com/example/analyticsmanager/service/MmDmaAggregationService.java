package com.example.analyticsmanager.service;


import com.example.analyticsmanager.entity.MmDmaAggrEntity;
import com.example.analyticsmanager.repo.MmDmaAggregationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MmDmaAggregationService extends Aggregator {
    private final MmDmaAggregationRepo mmDmaAggregationRepo;
    private final JdbcTemplate jdbcTemplate;

    public MmDmaAggregationService(JdbcTemplate jdbcTemplate, MmDmaAggregationRepo mmDmaAggregationRepo) {
        super(jdbcTemplate);
        this.mmDmaAggregationRepo = mmDmaAggregationRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void calculateAndSaveAggregation() {
        for (String tag : getClickTags()) {
            List<MmDmaAggrEntity> mmDmaAggregations = aggregateMmDma(tag);
            log.info("Saving {} mmDmaAggregations for tag {}", mmDmaAggregations.size(), tag);
            mmDmaAggregationRepo.saveAll(mmDmaAggregations);
        }
    }

    private List<MmDmaAggrEntity> aggregateMmDma(String tag) {
        String vTag = "v" + tag;
        String sql = "SELECT view_mm_dma, " +
            "COUNT(*) AS total_views, " +
            "(SUM(CASE WHEN visit_tag = ? THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS ctr, " +
            "(SUM(CASE WHEN (visit_tag = ? OR visit_tag = ?) THEN 1 ELSE 0 END) * 1000.0 / COUNT(*)) AS evpm " +
            "FROM public.joined_data " +
            "GROUP BY view_mm_dma " +
            "ORDER BY view_mm_dma";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, tag, tag, vTag);
        log.info("Getting mmDmaAggregations for tag {}", tag);

        return results.stream()
            .map(result -> new MmDmaAggrEntity(
                (String) result.get("view_mm_dma"),
                new Date(),
                (long) result.get("total_views"),
                (BigDecimal) result.get("ctr"),
                (BigDecimal) result.get("evpm"),
                tag
            ))
            .toList();
    }
}
