package com.example.analyticsmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class MmDmaAggrEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String mmDma;
    private Date updated;
    private Long views;
    private BigDecimal ctr;
    private BigDecimal evpm;
    private String tag;
}
