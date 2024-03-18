package com.database.integration.controller;

import com.database.integration.mysql.model.Homeworld;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.service.CharacterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0")
@AllArgsConstructor
public class CharacterController {

    private CharacterService service;

    @GetMapping(value = "/characters")
    public List<SwCharacter> getCharacters() {
        return service.getCharacters();
    }

    @GetMapping(value = "/homeworlds")
    public List<Homeworld> getHomeworlds() {
        return service.getHomeworlds();
    }
}
