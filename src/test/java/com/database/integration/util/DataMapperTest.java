package com.database.integration.util;

import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.importer.dto.MonogCharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
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

    private CharacterDto characterDto;
    private MysqlCharacter mysqlCharacter;

    @BeforeEach
    void setUp() {
        characterDto = new CharacterDto(0L, "mr_twardowski", "http://test_pic.jpg", "moon");

        mysqlCharacter = new MysqlCharacter();
        mysqlCharacter.setId(1L);
        mysqlCharacter.setName("mr_twardowski");
        mysqlCharacter.setPictureUrl("http://test_pic.jpg");

        MysqlHomeworld homeworld = new MysqlHomeworld();
        homeworld.setId(2L);
        homeworld.setName("moon");
        mysqlCharacter.setHomeworld(homeworld);
    }

    @Test
    void mysqlToMongoList() {
        List<MonogCharacterDto> expected = DataMapper.mysqlToMongo(Collections.singletonList(mysqlCharacter));
        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getName(), "mr_twardowski");
        assertEquals(expected.get(0).getPictureUrl(), "http://test_pic.jpg");
        assertEquals(expected.get(0).getHomeworld(), "moon");
        assertEquals(expected.get(0).getMysqlId(), 1L);
    }

    @Test
    void shouldMapCharacterDtoToMysqlCharacter() {
        MysqlCharacter expected = DataMapper.map(characterDto);
        assertEquals(expected.getName(), "mr_twardowski");
        assertEquals(expected.getPictureUrl(), "http://test_pic.jpg");
        assertNotNull(expected.getHomeworld());
        assertEquals(expected.getHomeworld().getName(), "moon");
    }

    @Test
    void testMysqlToMongoSingle() {
        MonogCharacterDto expected = DataMapper.mysqlToMongo(mysqlCharacter);
        assertEquals(expected.getName(), "mr_twardowski");
        assertEquals(expected.getPictureUrl(), "http://test_pic.jpg");
        assertEquals(expected.getHomeworld(), "moon");
        assertEquals(expected.getMysqlId(), 1L);
    }

    @Test
    void getHomeworld() {
        String name = "moon";
        MysqlHomeworld expected = DataMapper.getHomeworld(name);
        assertEquals(expected.getName(), "moon");

        name = null;
        expected = DataMapper.getHomeworld(name);
        assertNotNull(expected);
    }

    @Test
    void getHomeworlds() {
        CharacterListDto listDto = new CharacterListDto(new ArrayList<>());
        listDto.characters().add(characterDto);
        List<MysqlHomeworld> expected = DataMapper.getHomeworlds(listDto).stream().toList();

        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getName(), "moon");
    }

    @Test
    void asMap() {
        MysqlHomeworld h1 = new MysqlHomeworld();
        h1.setId(1L);
        h1.setName("moon");

        MysqlHomeworld h2 = new MysqlHomeworld();
        h2.setId(2L);
        h2.setName("earth");

        Set<MysqlHomeworld> homeworlds = new HashSet<>(){{
            add(h1);
            add(h2);
        }};

        Map<String, MysqlHomeworld> expected = DataMapper.asMap(homeworlds);
        assertNotNull(expected);
        assertEquals(expected.size(), 2);
        assertTrue(expected.containsKey("moon"));
        assertTrue(expected.containsKey("earth"));
        assertEquals(expected.get("moon"),
                MysqlHomeworld.builder().id(1L).name("moon").characters(Collections.emptySet()).build());
        assertEquals(expected.get("earth"),
                MysqlHomeworld.builder().id(2L).name("earth").characters(Collections.emptySet()).build());

    }

    @Test
    //TODO
    @Disabled
    void asMysqlCharacters() {
        MysqlHomeworld h1 = new MysqlHomeworld();
        h1.setId(1L);
        h1.setName("moon");

        MysqlHomeworld h2 = new MysqlHomeworld();
        h2.setId(2L);
        h2.setName("earth");

        Set<MysqlHomeworld> homeworlds = new HashSet<>(){{
            add(h1);
            add(h2);
        }};

//        Map<String, MysqlHomeworld> expected = DataMapper.asMap(homeworlds);
//        MysqlCharacter m1 = MysqlCharacter.builder().build();
    }

    @Test
    void shouldMapWithoutHomeworldProvided(){
        CharacterDto dto = new CharacterDto(1L, "mr_twardowski", "http://test_pic.jpg", null);
        MysqlCharacter mysqlCharacter = DataMapper.map(dto);
        assertEquals(mysqlCharacter.getName(), "mr_twardowski");
        assertEquals(mysqlCharacter.getPictureUrl(), "http://test_pic.jpg");
        assertNull(mysqlCharacter.getHomeworld());

        dto = new CharacterDto(1L, "mr_twardowski", "http://test_pic.jpg", "moon");
        MysqlHomeworld homeworld = MysqlHomeworld.builder().name("moon").build();
        mysqlCharacter = DataMapper.map(dto);
        assertEquals(mysqlCharacter.getName(), "mr_twardowski");
        assertEquals(mysqlCharacter.getPictureUrl(), "http://test_pic.jpg");
        assertEquals(mysqlCharacter.getHomeworld(), homeworld);

    }

    @Test
    void shouldMapWithHomeworldProvided(){
        CharacterDto dto = new CharacterDto(1L, "mr_twardowski", "http://test_pic.jpg", null);
        MysqlHomeworld homeworld = MysqlHomeworld.builder().name("moon").build();
        MysqlCharacter mysqlCharacter = DataMapper.map(dto);
        mysqlCharacter.setHomeworld(homeworld);
        assertEquals(mysqlCharacter.getName(), "mr_twardowski");
        assertEquals(mysqlCharacter.getPictureUrl(), "http://test_pic.jpg");
        assertEquals(mysqlCharacter.getHomeworld(), homeworld);

    }
}