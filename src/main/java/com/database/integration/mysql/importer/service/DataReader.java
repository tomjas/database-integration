package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DataReader {

    @Value("${database.integration.input.data}")
    private String inputFile;

    public CharacterListDto read() throws IOException {
        CharacterListDto dto;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(reader, CharacterListDto.class);
        }
        return dto;
    }
}
