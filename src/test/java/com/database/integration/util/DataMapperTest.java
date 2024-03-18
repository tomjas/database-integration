package com.database.integration.util;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataMapperTest {

    @Test
    void map() {
        CharacterListDto listDto = new CharacterListDto();
        CharacterDto dto = new CharacterDto();
        dto.setName("mr_twardowski");
        dto.setPic("http://test_pic.jpg");
        dto.setHomeworld("moon");
        listDto.getCharacters().add(dto);

        List<MysqlHomeworld> expected = DataMapper.map(listDto);
        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getName(), "moon");

        assertEquals(expected.get(0).getCharacters().size(), 1);
        assertEquals(expected.get(0).getCharacters().get(0).getHomeworld().getName(), "moon");
        assertEquals(expected.get(0).getCharacters().get(0).getName(), "mr_twardowski");
        assertEquals(expected.get(0).getCharacters().get(0).getPictureUrl(), "http://test_pic.jpg");
    }

    @Test
    void mysqlToMongo() {
        MysqlCharacter mysqlCharacter = new MysqlCharacter();
        mysqlCharacter.setId(1L);
        mysqlCharacter.setName("mr_twardowski");
        mysqlCharacter.setPictureUrl("http://test_pic.jpg");

        MysqlHomeworld homeworld = new MysqlHomeworld();
        homeworld.setId(2L);
        homeworld.setName("moon");

        mysqlCharacter.setHomeworld(homeworld);
        List<MonogCharacter> expected = DataMapper.mysqlToMongo(Collections.singletonList(mysqlCharacter));

        assertEquals(expected.size(), 1);
//        assertEquals(expected.get(0).getId(), 1L);
        assertEquals(expected.get(0).getName(), "mr_twardowski");
        assertEquals(expected.get(0).getPictureUrl(), "http://test_pic.jpg");
        assertEquals(expected.get(0).getHomeworld(), "moon");
    }
}