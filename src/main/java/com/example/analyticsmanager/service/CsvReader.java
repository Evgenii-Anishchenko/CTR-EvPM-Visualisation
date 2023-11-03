package com.example.analyticsmanager.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CsvReader {
    @Transactional
    void saveRecordsFromCSV(InputStream inputStream) throws IOException;

    List<?> readRecordsFromCSV(InputStream inputStream) throws IOException;
}
