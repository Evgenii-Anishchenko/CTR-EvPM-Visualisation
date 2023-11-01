package com.example.analyticsmanager.service;

import com.example.analyticsmanager.entity.ViewEvent;
import com.example.analyticsmanager.repo.ViewEventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    private final ViewEventRepo viewEventRepository;

    @Override
    public void saveRecordsFromCSV(File file) throws IOException {
        if (file != null) {
            List<ViewEvent> records = readRecordsFromCSV(file);
            viewEventRepository.saveAll(records);
        }
    }

    @Override
    public List<ViewEvent> readRecordsFromCSV(File file) throws IOException {
        log.info("Reading records from CSV file {}", file.getName());
        List<ViewEvent> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    try {
                        Date date = dateFormat.parse(parts[0]);
                        ViewEvent viewEvent = new ViewEvent(
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
                        records.add(viewEvent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return records;
    }
}
