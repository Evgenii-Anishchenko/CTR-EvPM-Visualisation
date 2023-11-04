package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.VisitEvent;
import com.example.analyticsmanager.repo.VisitEventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitEventService implements CsvReader {

    private static final int BATCH_SIZE = 500;
    private final VisitEventRepo visitEventRepo;

    @Override
    public void saveRecordsFromCSV(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                boolean firstLine = true;
                List<VisitEvent> batch = new ArrayList<>(BATCH_SIZE);

                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false; // Skip header line
                        continue;
                    }
                    VisitEvent visitEvent = parseLineToVisitEvent(line);
                    if (visitEvent != null) {
                        batch.add(visitEvent);
                    }
                    if (batch.size() >= BATCH_SIZE) {
                        visitEventRepo.saveAll(batch);
                        batch.clear();
                    }
                }
                if (!batch.isEmpty()) {
                    visitEventRepo.saveAll(batch);
                }
            }
        }
    }

    private VisitEvent parseLineToVisitEvent(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            return new VisitEvent(
                UUID.fromString(parts[0]),
                parts[1]
            );
        }
        return null;
    }
}
