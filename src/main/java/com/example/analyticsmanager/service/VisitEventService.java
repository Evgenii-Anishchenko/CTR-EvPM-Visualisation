package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.VisitEvent;
import com.example.analyticsmanager.repo.VisitEventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitEventService implements CsvReader {

    private final VisitEventRepo visitEventRepo;

    @Override
    public void saveRecordsFromCSV(File file) throws IOException {
        if (file != null) {
            List<VisitEvent> records = readRecordsFromCSV(file);
            visitEventRepo.saveAll(records);
        }
    }

    @Override
    public List<VisitEvent> readRecordsFromCSV(File file) throws IOException {
        log.info("Reading records from CSV file {}", file.getName());
        List<VisitEvent> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    VisitEvent visitEvent = new VisitEvent(
                        UUID.fromString(parts[0]),
                        parts[1]
                    );
                    records.add(visitEvent);
                }
            }
        }
        return records;
    }
}


