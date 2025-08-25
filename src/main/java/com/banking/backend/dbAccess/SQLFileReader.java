package com.banking.backend.dbAccess;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SQLFileReader {

    public static String readSQL(String path) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
        catch(IOException exception){
            throw new RuntimeException("Failed to read SQL file: " + path, exception);
        }
    }
}
