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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0")
@RequiredArgsConstructor
public class SwCharacterController {

    private final SwCharacterService characterService;
    private final SwHomeworldService homeworldService;

    @GetMapping(value = "/characters")
    public List<SwCharacter> getCharacters(@RequestParam(required = false) String name) {
        return characterService.getCharacters(name);
    }

    @GetMapping(value = "/characters/{id}")
    public SwCharacter getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping(value = "/homeworlds")
    public List<SwHomeworld> getHomeworlds(@RequestParam(required = false) String name) {
        return homeworldService.getHomeworlds(name);
    }

    @GetMapping(value = "/homeworlds/{id}")
    public SwHomeworld getHomeworldById(@PathVariable Long id) {
        return homeworldService.getHomeworldById(id);
    }

    @PostMapping(value = "/characters")
    public SwCharacter add(@Valid @RequestBody SwCharacterInDto dto) {
        return characterService.add(dto);
    }

    @PutMapping(value = "/characters/{id}")
    public SwCharacter update(@Valid @RequestBody SwCharacterInDto dto, @PathVariable Long id) {
        return characterService.update(dto, id);
    }

}
