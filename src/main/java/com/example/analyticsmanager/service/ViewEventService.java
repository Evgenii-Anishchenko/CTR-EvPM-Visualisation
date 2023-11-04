package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.ViewEvent;
import com.example.analyticsmanager.repo.ViewEventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewEventService implements CsvReader {

    private static final int BATCH_SIZE = 500;
    private final ViewEventRepo viewEventRepository;

    @Transactional
    public void saveRecordsFromCSV(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            List<ViewEvent> records = new ArrayList<>(BATCH_SIZE);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false; // Skip header line
                        continue;
                    }
                    ViewEvent viewEvent = parseLineToViewEvent(line);
                    if (viewEvent != null) {
                        records.add(viewEvent);
                    }

                    // When the batch is full, save and clear the list
                    if (records.size() >= BATCH_SIZE) {
                        viewEventRepository.saveAll(records);
                        records.clear();
                    }
                }

                // Save any remaining records that did not fill the last batch
                if (!records.isEmpty()) {
                    viewEventRepository.saveAll(records);
                }
            }
        }
    }

    private ViewEvent parseLineToViewEvent(String line) {
        String[] parts = line.split(",");
        if (parts.length == 10) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date date = dateFormat.parse(parts[0]);
                return new ViewEvent(
                    date,
                    UUID.fromString(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]),
                    parts[5],
                    parts[6],
                    parts[7],
                    parts[8],
                    parts[9]
                );
            } catch (ParseException e) {
                log.error("Error parsing date", e);
            }
        }
        return null;
    }
}

