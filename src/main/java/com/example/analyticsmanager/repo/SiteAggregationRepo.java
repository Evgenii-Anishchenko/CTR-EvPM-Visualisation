package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.SiteAggrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteAggregationRepo extends JpaRepository<SiteAggrEntity, String> {
    public List<SiteAggrEntity> findAllByTag(String tag);
}
