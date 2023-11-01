package com.example.analyticsmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteAggrEntity {
    @Id
    private String siteId;
    private Date updated;
    private Long views;
    private BigDecimal ctr;
    private BigDecimal evpm;
    private String tag;
}
