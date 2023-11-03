package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.MmDmaAggrEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MmDmaAggregationRepo extends JpaRepository<MmDmaAggrEntity, String> {
    public Page<MmDmaAggrEntity> findAllByTag(String tag, Pageable pageable);
}
