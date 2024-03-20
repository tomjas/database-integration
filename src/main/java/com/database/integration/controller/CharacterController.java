package com.database.integration.controller;

import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.service.MysqlCharacterService;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0")
@RequiredArgsConstructor
public class CharacterController {

    private final MysqlCharacterService mysqlCharacterService;

    @GetMapping(value = "/mysql/characters")
    public List<MysqlCharacter> getMysqlCharacters() {
        return mysqlCharacterService.getCharacters();
    }

    @GetMapping(value = "/mysql/homeworlds")
    public List<MysqlHomeworld> getMysqlHomeworlds() {
        return mysqlCharacterService.getHomeworlds();
    }

    @PostMapping(value = "/mysql/add")
    public MysqlCharacter add(@Valid @RequestBody CharacterDto dto) {
        return mysqlCharacterService.add(dto);
    }

    @PutMapping(value = "/mysql/characters/{id}")
    public MysqlCharacter update(@Valid @RequestBody CharacterDto dto, @PathVariable Long id) {
        //TODO
        return null;
    }

}
