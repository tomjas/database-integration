package com.database.integration.util;

import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.importer.dto.SwCharacterWrapperDto;
import com.database.integration.mysql.importer.dto.SwCharacterOutDto;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.model.SwCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataMapperTest {

    private SwCharacterInDto dto;
    private SwCharacter character;

    @BeforeEach
    void setUp() {
        dto = new SwCharacterInDto(0L, "mr_twardowski", "http://test_pic.jpg", "moon");

        character = new SwCharacter();
        character.setId(1L);
        character.setName("mr_twardowski");
        character.setPictureUrl("http://test_pic.jpg");

        SwHomeworld homeworld = new SwHomeworld();
        homeworld.setId(2L);
        homeworld.setName("moon");
        character.setHomeworld(homeworld);
    }

    @Test
    void mysqlToMongoList() {
        List<SwCharacterOutDto> expected = DataMapper.mysqlToMongo(Collections.singletonList(character));
        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getName(), "mr_twardowski");
        assertEquals(expected.get(0).getPictureUrl(), "http://test_pic.jpg");
        assertEquals(expected.get(0).getHomeworld(), "moon");
        assertEquals(expected.get(0).getMysqlId(), 1L);
    }

    @Test
    void shouldMapCharacterDtoToMysqlCharacter() {
        SwCharacter expected = DataMapper.map(dto);
        assertEquals(expected.getName(), "mr_twardowski");
        assertEquals(expected.getPictureUrl(), "http://test_pic.jpg");
        assertNotNull(expected.getHomeworld());
        assertEquals(expected.getHomeworld().getName(), "moon");
    }

    @Test
    void testMysqlToMongoSingle() {
        SwCharacterOutDto expected = DataMapper.mysqlToMongo(character);
        assertEquals(expected.getName(), "mr_twardowski");
        assertEquals(expected.getPictureUrl(), "http://test_pic.jpg");
        assertEquals(expected.getHomeworld(), "moon");
        assertEquals(expected.getMysqlId(), 1L);
    }

    @Test
    void getHomeworld() {
        String name = "moon";
        SwHomeworld expected = DataMapper.getHomeworld(name);
        assertEquals(expected.getName(), "moon");

        name = null;
        expected = DataMapper.getHomeworld(name);
        assertNotNull(expected);
    }

    @Test
    void getHomeworlds() {
        SwCharacterWrapperDto wrapperDto = new SwCharacterWrapperDto(new ArrayList<>());
        wrapperDto.characters().add(dto);
        List<SwHomeworld> expected = DataMapper.getHomeworlds(wrapperDto).stream().toList();

        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getName(), "moon");
    }

    @Test
    void asMap() {
        SwHomeworld h1 = new SwHomeworld();
        h1.setId(1L);
        h1.setName("moon");

        SwHomeworld h2 = new SwHomeworld();
        h2.setId(2L);
        h2.setName("earth");

        Set<SwHomeworld> homeworlds = new HashSet<>(){{
            add(h1);
            add(h2);
        }};

        Map<String, SwHomeworld> expected = DataMapper.asMap(homeworlds);
        assertNotNull(expected);
        assertEquals(expected.size(), 2);
        assertTrue(expected.containsKey("moon"));
        assertTrue(expected.containsKey("earth"));
        assertEquals(expected.get("moon"),
                SwHomeworld.builder().id(1L).name("moon").characters(Collections.emptySet()).build());
        assertEquals(expected.get("earth"),
                SwHomeworld.builder().id(2L).name("earth").characters(Collections.emptySet()).build());

    }

    @Test
    //TODO
    @Disabled
    void asMysqlCharacters() {
        SwHomeworld h1 = new SwHomeworld();
        h1.setId(1L);
        h1.setName("moon");

        SwHomeworld h2 = new SwHomeworld();
        h2.setId(2L);
        h2.setName("earth");

        Set<SwHomeworld> homeworlds = new HashSet<>(){{
            add(h1);
            add(h2);
        }};

//        Map<String, MysqlHomeworld> expected = DataMapper.asMap(homeworlds);
//        MysqlCharacter m1 = MysqlCharacter.builder().build();
    }

    @Test
    void shouldMapWithoutHomeworldProvided(){
        SwCharacterInDto dto = new SwCharacterInDto(1L, "mr_twardowski", "http://test_pic.jpg", null);
        SwCharacter character = DataMapper.map(dto);
        assertEquals(character.getName(), "mr_twardowski");
        assertEquals(character.getPictureUrl(), "http://test_pic.jpg");
        assertNull(character.getHomeworld());

        dto = new SwCharacterInDto(1L, "mr_twardowski", "http://test_pic.jpg", "moon");
        SwHomeworld homeworld = SwHomeworld.builder().name("moon").build();
        character = DataMapper.map(dto);
        assertEquals(character.getName(), "mr_twardowski");
        assertEquals(character.getPictureUrl(), "http://test_pic.jpg");
        assertEquals(character.getHomeworld(), homeworld);

    }

    @Test
    void shouldMapWithHomeworldProvided(){
        SwCharacterInDto dto = new SwCharacterInDto(1L, "mr_twardowski", "http://test_pic.jpg", null);
        SwHomeworld homeworld = SwHomeworld.builder().name("moon").build();
        SwCharacter character = DataMapper.map(dto);
        character.setHomeworld(homeworld);
        assertEquals(character.getName(), "mr_twardowski");
        assertEquals(character.getPictureUrl(), "http://test_pic.jpg");
        assertEquals(character.getHomeworld(), homeworld);

    }
}