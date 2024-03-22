package com.database.integration.controller;

import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.importer.service.SwCharacterService;
import com.database.integration.mysql.importer.service.SwHomeworldService;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.model.SwHomeworld;
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
public class SwCharacterController {

    private final SwCharacterService characterService;
    private final SwHomeworldService homeworldService;

    @GetMapping(value = "/characters")
    public List<SwCharacter> getCharacters() {
        return characterService.getCharacters();
    }

    @GetMapping(value = "/characters/name/{name}")
    public SwCharacter getCharacterByName(@PathVariable String name) {
        return characterService.getCharacterByName(name);
    }

    @GetMapping(value = "/characters/id/{id}")
    public SwCharacter getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping(value = "/homeworlds")
    public List<SwHomeworld> getHomeworlds() {
        return homeworldService.getHomeworlds();
    }

    @GetMapping(value = "/homeworlds/name/{name}")
    public SwHomeworld getHomeworldByName(@PathVariable String name) {
        return homeworldService.getHomeworldByName(name);
    }

    @GetMapping(value = "/homeworlds/id/{id}")
    public SwHomeworld getHomeworldById(@PathVariable Long id) {
        return homeworldService.getHomeworldById(id);
    }

    @PostMapping(value = "/characters/add")
    public SwCharacter add(@Valid @RequestBody SwCharacterInDto dto) {
        return characterService.add(dto);
    }

    @PutMapping(value = "/characters/id/{id}")
    public SwCharacter update(@Valid @RequestBody SwCharacterInDto dto, @PathVariable Long id) {
        return characterService.update(dto, id);
    }

}
