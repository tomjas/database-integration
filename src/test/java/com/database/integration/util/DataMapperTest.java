package com.database.integration.util;

import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.importer.dto.MonogCharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void shouldMapCharacterListDtoToListOfMysqlHomeworld() {
        CharacterListDto listDto = new CharacterListDto(new ArrayList<>());
        listDto.characters().add(characterDto);

        List<MysqlHomeworld> expected = DataMapper.map(listDto);
        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getName(), "moon");

        assertEquals(expected.get(0).getCharacters().size(), 1);
        assertEquals(expected.get(0).getCharacters().get(0).getHomeworld().getName(), "moon");
        assertEquals(expected.get(0).getCharacters().get(0).getName(), "mr_twardowski");
        assertEquals(expected.get(0).getCharacters().get(0).getPictureUrl(), "http://test_pic.jpg");
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
}