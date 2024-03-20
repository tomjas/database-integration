package com.database.integration.mysql.importer.service;

import com.database.integration.mongodb.service.IntegrationService;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
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
    private final MysqlCharacterRepository mysqlRepository;
    private final DataReader reader;

    @Override
    public void run(String... args) throws Exception {
        CharacterListDto characterListDto = reader.read();
        importerService.persist(characterListDto);
        List<MysqlCharacter> mysqlCharacters = mysqlRepository.findAll();
        integrationService.send(mysqlCharacters);
    }
}
