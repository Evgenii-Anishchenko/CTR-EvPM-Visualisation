package com.example.analyticsmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChartUnitEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long views;
    private Long clicks;
    private Date timestamp;
    private BigDecimal ctr;
    private BigDecimal evpm;
    private String tag;
    @Enumerated(EnumType.ORDINAL)
    private TimeInterval timeInterval;

    public enum TimeInterval {
        HOUR(1),
        DAY(2),
        WEEK(3),
        MONTH(4);
        int intervalId;

        TimeInterval(int intervalId) {
            this.intervalId = intervalId;
        }

    }

}
