package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.MmDmaAggrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MmDmaAggregationRepo extends JpaRepository<MmDmaAggrEntity, String> {
}
