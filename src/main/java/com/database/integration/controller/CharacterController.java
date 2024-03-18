package com.database.integration.controller;

import com.database.integration.mysql.importer.service.MysqlCharacterService;
import com.database.integration.mysql.model.Homeworld;
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

    private MysqlCharacterService service;

    @GetMapping(value = "/characters")
    public List<MysqlCharacter> getCharacters() {
        return service.getCharacters();
    }

    @GetMapping(value = "/homeworlds")
    public List<Homeworld> getHomeworlds() {
        return service.getHomeworlds();
    }
}
