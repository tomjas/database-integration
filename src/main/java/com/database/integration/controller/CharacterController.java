package com.database.integration.controller;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.service.IntegrationService;
import com.database.integration.mongodb.service.MongoCharacterService;
import com.database.integration.mysql.importer.service.MysqlCharacterService;
import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.model.MysqlCharacter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0")
@AllArgsConstructor
public class CharacterController {

    private MysqlCharacterService mysqlCharacterService;
    private MongoCharacterService mongoCharacterService;
    private IntegrationService integrationService;

    @GetMapping(value = "/mysql/characters")
    public List<MysqlCharacter> getMysqlCharacters() {
        return mysqlCharacterService.getCharacters();
    }

    @GetMapping(value = "/mysql/homeworlds")
    public List<MysqlHomeworld> getMysqlHomeworlds() {
        return mysqlCharacterService.getHomeworlds();
    }

    @GetMapping(value = "/mongo/characters")
    public List<MonogCharacter> getMongoCharacters() {
        return mongoCharacterService.getCharacters();
    }

    @GetMapping(value = "/transfer")
    public void transfer() {
        integrationService.transfer();
    }
}
