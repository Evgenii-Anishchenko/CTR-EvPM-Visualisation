package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.MmDmaAggrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmDmaAggregationRepo extends JpaRepository<MmDmaAggrEntity, String> {
    public List<MmDmaAggrEntity> findAllByTag(String tag);
}
