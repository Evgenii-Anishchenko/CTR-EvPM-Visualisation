package com.example.analyticsmanager.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ViewEventDto {
    private Date reg_time;
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
