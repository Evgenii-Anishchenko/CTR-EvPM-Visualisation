package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.SiteAggrEntity;
import com.example.analyticsmanager.repo.SiteAggregationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SiteAggregationService extends Aggregator {

    private final SiteAggregationRepo siteAggregationRepo;
    private final JdbcTemplate jdbcTemplate;

    public SiteAggregationService(JdbcTemplate jdbcTemplate, SiteAggregationRepo siteAggregationRepo) {
        super(jdbcTemplate);
        this.siteAggregationRepo = siteAggregationRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<SiteAggrEntity> getAggregation(String tag, Pageable pageable) {
        return siteAggregationRepo.findAllByTag(tag, pageable);
    }

    @Override
    public void calculateAndSaveAggregation() {
        for (String tag : getClickTags()) {
            List<SiteAggrEntity> mmDmaAggregations = aggregateSite(tag);
            log.info("Saving {} mmDmaAggregations for tag {}", mmDmaAggregations.size(), tag);
            siteAggregationRepo.saveAll(mmDmaAggregations);
        }
    }

    private List<SiteAggrEntity> aggregateSite(String tag) {
        String vTag = "v" + tag;
        String sql = "SELECT view_site_id, " +
            "COUNT(*) AS total_views, " +
            "(SUM(CASE WHEN visit_tag = ? THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS ctr, " +
            "(SUM(CASE WHEN (visit_tag = ? OR visit_tag = ?) THEN 1 ELSE 0 END) * 1000.0 / COUNT(*)) AS evpm " +
            "FROM public.joined_data " +
            "GROUP BY view_site_id " +
            "ORDER BY view_site_id";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, tag, tag, vTag);
        log.info("Getting siteAggregations for tag {}", tag);

        return results.stream()
            .map(result -> {
                SiteAggrEntity entity = new SiteAggrEntity();
                entity.setSiteId((String) result.get("view_site_id"));
                entity.setUpdated(new Date());
                entity.setViews((long) result.get("total_views"));
                entity.setCtr((BigDecimal) result.get("ctr"));
                entity.setEvpm((BigDecimal) result.get("evpm"));
                entity.setTag(tag);
                return entity;
            })
            .toList();
    }
}
