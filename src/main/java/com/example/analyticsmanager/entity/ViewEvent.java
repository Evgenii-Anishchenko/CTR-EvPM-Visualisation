package com.example.analyticsmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ViewEvent {
    private Date reg_time;
    @Id
    private UUID uid;
    private int fc_imp_chk;
    private int fc_time_chk;
    private int utmtr;
    private String mm_dma;
    private String osName;
    private String model;
    private String hardware;
    private String site_id;
}
