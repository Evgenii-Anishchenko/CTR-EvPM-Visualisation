package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.SiteAggrEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteAggregationRepo extends JpaRepository<SiteAggrEntity, String> {
    Page<SiteAggrEntity> findAllByTag(String tag, Pageable pageable);
}
