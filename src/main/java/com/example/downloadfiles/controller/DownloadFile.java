package com.example.downloadfiles.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DownloadFile {

    @GetMapping("/hello")
    public String hell() {
        return "Hello spring boot";
    }

    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile() {

        System.out.println("Download");
        try {
//        CSVData csv1 = new CSVData();
//        csv1.setId("1");
//        csv1.setName("test1");
//        csv1.setName("5601");
//
//        CSVData csv2 = new CSVData();
//        csv2.setId("1");
//        csv2.setName("test1");
//        csv2.setName("5601");
//
//        List<CSVData> csvData = new ArrayList<>();
//        csvData.add(csv1);
//        csvData.add(csv2);
//
//        StringBuilder fileContent = new StringBuilder("ID, NAME, NUMBER\n");
//
//        for (CSVData csv : csvData) {
//            fileContent.append(csv.getId()).append(csv.getName()).append(",").append(csv.getNumber()).append("\n");
//        }

       // String filename = "/Users/thienle/Documents/download-files/csvdata.csv";
        String filename = "/Users/thienle/Documents/PrintingService/pom.xml";

//        FileWriter fileWriter = new FileWriter(filename);
//        fileWriter.write(fileContent.toString());
//        fileWriter.flush();
//
//        fileWriter.close();

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

    @GetMapping("/download-text")
    public ResponseEntity<Object> getDownload() {
        List<String> content = new ArrayList<>();
        content.add("Hello Spring boot");
        content.add("Download a text");

        String fileName = "downloadtext.txt";

        try {

            Path path = Paths.get("/Users/thienle/Documents/testDir");
            Path tempFile = Files.createTempFile(path,"temp", null);

            Files.write(tempFile, content, StandardOpenOption.APPEND);
            System.out.println(tempFile.getFileName() + "\n" + tempFile);

            String tempFileContent = Files
                    .lines(tempFile, StandardCharsets.UTF_8)
                    .collect(Collectors.joining(System.lineSeparator()));
            System.out.println(tempFileContent);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(tempFile.toFile()));

            HttpHeaders headers = new HttpHeaders();

            //headers.add("Content-Transfer-Encoding", "binary");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            tempFile.toFile().deleteOnExit();

            //System.out.println(tempFile.toFile().delete());
            return ResponseEntity.ok().headers(headers).contentLength(tempFile.toFile().length()).contentType(MediaType.parseMediaType("application/txt"))
                    .body(resource);

            //return new ResponseEntity<>(stringList, headers, HttpStatus.OK);
        } catch (IOException fileEx) {
            System.out.println("File not found");
        }

        return new ResponseEntity<>("Bad request", HttpStatus.INTERNAL_SERVER_ERROR);
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


