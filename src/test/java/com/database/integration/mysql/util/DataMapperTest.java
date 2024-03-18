package com.database.integration.mysql.util;

import com.database.integration.mysql.importer.dto.SwCharacterDto;
import com.database.integration.mysql.importer.dto.SwCharacterListDto;
import com.database.integration.mysql.model.Homeworld;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataMapperTest {

    @Test
    void map() {
        SwCharacterListDto listDto = new SwCharacterListDto();
        SwCharacterDto dto = new SwCharacterDto();
        dto.setName("test_name");
        dto.setPic("http://test_pic.jpg");
        dto.setHomeworld("moon");
        listDto.getCharacters().add(dto);

        List<Homeworld> expected = DataMapper.map(listDto);
        assertEquals(expected.get(0).getName(), "moon");
        assertEquals(expected.get(0).getSwCharacters().get(0).getHomeworld().getName(), "moon");
        assertEquals(expected.get(0).getSwCharacters().get(0).getName(), "test_name");
        assertEquals(expected.get(0).getSwCharacters().get(0).getPictureUrl(), "http://test_pic.jpg");
    }
}