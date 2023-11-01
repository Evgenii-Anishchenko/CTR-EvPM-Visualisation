package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.ChartUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartUnitRepo extends JpaRepository<ChartUnitEntity, Long> {
}
