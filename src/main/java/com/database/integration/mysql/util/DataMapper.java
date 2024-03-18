package com.database.integration.mysql.util;

import com.database.integration.mysql.importer.dto.SwCharacterDto;
import com.database.integration.mysql.importer.dto.SwCharacterListDto;
import com.database.integration.mysql.model.Homeworld;
import com.database.integration.mysql.model.MysqlCharacter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataMapper {

    public static List<Homeworld> map(SwCharacterListDto listDto) {
        Map<String, Homeworld> homeworldMap = new HashMap<>();
        for (SwCharacterDto swCharacterDto : listDto.getCharacters()) {
            homeworldMap.computeIfAbsent(swCharacterDto.getHomeworld(), v -> new Homeworld()).setName(swCharacterDto.getHomeworld());
            MysqlCharacter mysqlCharacter = getSwCharacter(swCharacterDto, homeworldMap);
            homeworldMap.get(swCharacterDto.getHomeworld()).getCharacters().add(mysqlCharacter);
        }
        return new ArrayList<>(homeworldMap.values());
    }

    private static MysqlCharacter getSwCharacter(SwCharacterDto swCharacterDto, Map<String, Homeworld> homeworldMap) {
        return MysqlCharacter.builder()
                .name(swCharacterDto.getName())
                .pictureUrl(swCharacterDto.getPic())
                .homeworld(homeworldMap.get(swCharacterDto.getHomeworld()))
                .build();
    }

}
