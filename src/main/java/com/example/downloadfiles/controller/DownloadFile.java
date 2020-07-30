package com.example.downloadfiles.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DownloadFile {

    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile() {


        try {
        CSVData csv1 = new CSVData();
        csv1.setId("1");
        csv1.setName("test1");
        csv1.setName("5601");

        CSVData csv2 = new CSVData();
        csv2.setId("1");
        csv2.setName("test1");
        csv2.setName("5601");

        List<CSVData> csvData = new ArrayList<>();
        csvData.add(csv1);
        csvData.add(csv2);

        StringBuilder fileContent = new StringBuilder("ID, NAME, NUMBER\n");

        for (CSVData csv : csvData) {
            fileContent.append(csv.getId()).append(csv.getName()).append(",").append(csv.getName()).append("\n");
        }

        String filename = "/Users/thienle/Documents/download-files/csvdata.csv";

        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(fileContent.toString());
        fileWriter.flush();

        fileWriter.close();

        File file = new File(filename);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
        } catch (IOException io) {

        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private class CSVData {
        String id;
        String name;
        String number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "CSVData{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", number='" + number + '\'' +
                    '}';
        }
    }
}


