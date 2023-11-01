package com.example.analyticsmanager.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CsvReader {
    @Transactional
    void saveRecordsFromCSV(File file) throws IOException;

    List<?> readRecordsFromCSV(File file) throws IOException;
}
