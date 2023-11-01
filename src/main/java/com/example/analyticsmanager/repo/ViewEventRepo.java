package com.example.analyticsmanager.repo;

import com.example.analyticsmanager.entity.ViewEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViewEventRepo extends JpaRepository<ViewEvent, UUID> {
}
