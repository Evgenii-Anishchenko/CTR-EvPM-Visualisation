package com.example.analyticsmanager.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VisitEventDto {
    private UUID uid;
    private String tag;
}
