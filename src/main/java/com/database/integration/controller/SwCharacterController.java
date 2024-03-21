package com.database.integration.controller;

import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.importer.service.SwCharacterService;
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

    private final SwCharacterService swCharacterService;

    @GetMapping(value = "/mysql/characters")
    public List<SwCharacter> getCharacters() {
        return swCharacterService.getCharacters();
    }

    @GetMapping(value = "/mysql/homeworlds")
    public List<SwHomeworld> getHomeworlds() {
        return swCharacterService.getHomeworlds();
    }

    @PostMapping(value = "/mysql/add")
    public SwCharacter add(@Valid @RequestBody SwCharacterInDto dto) {
        return swCharacterService.add(dto);
    }

    @PutMapping(value = "/mysql/characters/{id}")
    public SwCharacter update(@Valid @RequestBody SwCharacterInDto dto, @PathVariable Long id) {
        return swCharacterService.update(dto, id);
    }

}
