package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.VisitEvent;
import com.example.analyticsmanager.repo.VisitEventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitEventService implements CsvReader {

    private final VisitEventRepo visitEventRepo;

    // Method now accepts InputStream instead of File
    @Override
    public void saveRecordsFromCSV(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            List<VisitEvent> records = readRecordsFromCSV(inputStream);
            visitEventRepo.saveAll(records);
        }
    }

    // Adjusted to read from InputStream
    @Override
    public List<VisitEvent> readRecordsFromCSV(InputStream inputStream) throws IOException {
        log.info("Reading records from CSV");
        List<VisitEvent> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
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
