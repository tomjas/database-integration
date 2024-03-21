package com.database.integration.mysql.importer.service;

import com.database.integration.mongodb.service.IntegrationService;
import com.database.integration.mysql.importer.dto.SwCharacterWrapperDto;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.repository.SwCharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImporterStartup implements CommandLineRunner {

    private final ImporterService importerService;
    private final IntegrationService integrationService;
    private final SwCharacterRepository repository;
    private final DataReader reader;

    @Override
    public void run(String... args) throws Exception {
        SwCharacterWrapperDto swCharacterListDto = reader.read();
        importerService.persist(swCharacterListDto);
        List<SwCharacter> swCharacters = repository.findAll();
        integrationService.send(swCharacters);
    }
}
