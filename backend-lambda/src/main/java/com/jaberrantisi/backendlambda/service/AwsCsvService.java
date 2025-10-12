package com.jaberrantisi.backendlambda.service;

import com.jaberrantisi.backendlambda.model.CostEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;


import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AwsCsvService {

    public List<CostEntry> parseCsv(byte[] csvBytes) throws IOException {
        List<CostEntry> entries = new ArrayList<>();

        String csvStr = new String(csvBytes, StandardCharsets.UTF_8);
        if (csvStr.startsWith("\uFEFF")) {
            csvStr = csvStr.substring(1);
        }

        try (Reader reader = new StringReader(csvStr);
             CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreEmptyLines(true)
                     .setIgnoreSurroundingSpaces(true)
                     .setTrim(true)
                     .build())) {

            List<String> headers = parser.getHeaderNames();
            List<String> serviceColumns = headers.subList(1, headers.size() - 1);

            for (CSVRecord record : parser) {
                String dateStr = record.get(headers.get(0));
                if (dateStr == null || !dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) continue;

                LocalDate date = LocalDate.parse(dateStr);

                for (String service : serviceColumns) {
                    String value = record.get(service);
                    if (value == null || value.isBlank()) continue;

                    double amount;
                    try {
                        amount = Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    entries.add(CostEntry.builder()
                            .date(date)
                            .service(service)
                            .amount(amount)
                            .build());
                }
            }
        }
        return entries;
    }

    public Map<String, Map<String, Double>> groupByDate(List<CostEntry> entries) {
        Map<String, Map<String, Double>> chartData = new TreeMap<>();

        for (CostEntry entry : entries) {
            chartData
                    .computeIfAbsent(entry.getDate().toString(), d -> new HashMap<>())
                    .put(entry.getService(), entry.getAmount());
        }

        return chartData;
    }

}












