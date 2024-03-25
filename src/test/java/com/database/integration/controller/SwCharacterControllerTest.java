package com.database.integration.controller;

import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.importer.service.SwCharacterService;
import com.database.integration.mysql.importer.service.SwHomeworldService;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.model.SwHomeworld;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
class SwCharacterControllerTest {

    @MockBean
    private SwCharacterService characterService;

    @MockBean
    private SwHomeworldService homeworldService;

    @Autowired
    private MockMvc mockMvc;

    private SwHomeworld homeworld;
    private SwCharacter character;

    private final List<SwCharacter> characters = new ArrayList<>();
    private final List<SwHomeworld> homeworlds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        homeworld = SwHomeworld.builder()
                .name("test homeworld")
                .id(321L)
                .build();
        character = SwCharacter.builder()
                .name("test name")
                .homeworld(homeworld)
                .pictureUrl("http://test_url.com")
                .id(123L)
                .build();
        characters.add(character);
        homeworlds.add(homeworld);
    }

    @Test
    void getCharacters() throws Exception {
        when(characterService.getCharacters(null)).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/characters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("[]"));

        when(characterService.getCharacters(null)).thenReturn(characters);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/characters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$[0].name", is("test name")),
                        jsonPath("$[0].homeworld.id", is(321)),
                        jsonPath("$[0].homeworld.name", is("test homeworld")),
                        jsonPath("$[0].pictureUrl", is("http://test_url.com")),
                        jsonPath("$[0].id", is(123)));

        when(characterService.getCharacters(anyString())).thenReturn(characters);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/characters?name=test name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$[0].name", is("test name")),
                        jsonPath("$[0].homeworld.id", is(321)),
                        jsonPath("$[0].homeworld.name", is("test homeworld")),
                        jsonPath("$[0].pictureUrl", is("http://test_url.com")),
                        jsonPath("$[0].id", is(123)));

    }

    @Test
    void getCharacterById() throws Exception {
        when(characterService.getCharacterById(anyLong())).thenReturn(character);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/characters/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$.name", is("test name")),
                        jsonPath("$.homeworld.id", is(321)),
                        jsonPath("$.homeworld.name", is("test homeworld")),
                        jsonPath("$.pictureUrl", is("http://test_url.com")),
                        jsonPath("$.id", is(123)));
    }

    @Test
    void getHomeworlds() throws Exception {
        when(homeworldService.getHomeworlds(null)).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/homeworlds"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("[]"));

        when(homeworldService.getHomeworlds(null)).thenReturn(homeworlds);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/homeworlds"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$[0].name", is("test homeworld")),
                        jsonPath("$[0].id", is(321)));

        when(homeworldService.getHomeworlds(anyString())).thenReturn(homeworlds);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/homeworlds?name=test homeworld"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$[0].name", is("test homeworld")),
                        jsonPath("$[0].id", is(321)));
    }

    @Test
    void getHomeworldById() throws Exception {
        when(homeworldService.getHomeworldById(anyLong())).thenReturn(homeworld);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/homeworlds/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$.name", is("test homeworld")),
                        jsonPath("$.id", is(321)));
    }

    @Test
    void add() throws Exception {
        SwCharacterInDto dto = new SwCharacterInDto(333, "test name", "http://test_url.com", "test homeworld");
        when(characterService.add(any(SwCharacterInDto.class))).thenReturn(character);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/characters")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$.name", is("test name")),
                        jsonPath("$.homeworld.id", is(321)),
                        jsonPath("$.homeworld.name", is("test homeworld")),
                        jsonPath("$.pictureUrl", is("http://test_url.com")),
                        jsonPath("$.id", is(123)));
    }

    @Test
    void update() throws Exception {
        SwCharacterInDto dto = new SwCharacterInDto(333, "test name", "http://test_url.com", "test homeworld");
        when(characterService.update(any(SwCharacterInDto.class), anyLong())).thenReturn(character);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1.0/characters/123")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$.name", is("test name")),
                        jsonPath("$.homeworld.id", is(321)),
                        jsonPath("$.homeworld.name", is("test homeworld")),
                        jsonPath("$.pictureUrl", is("http://test_url.com")),
                        jsonPath("$.id", is(123)));
    }
}