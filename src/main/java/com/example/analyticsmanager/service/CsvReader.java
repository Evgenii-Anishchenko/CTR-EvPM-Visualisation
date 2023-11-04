package com.example.analyticsmanager.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

public interface CsvReader {
    @Transactional
    void saveRecordsFromCSV(InputStream inputStream) throws IOException;

}
