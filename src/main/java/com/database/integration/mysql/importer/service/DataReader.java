package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.SwCharacterListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class DataReader {

    @Value("${database.integration.input.data}")
    private String inputFile;

    public SwCharacterListDto read() throws IOException {
        SwCharacterListDto dto;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(reader, SwCharacterListDto.class);
        }
        return dto;
    }
}
