package com.database.integration.util;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.model.MysqlCharacter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataMapper {

    public static List<MysqlHomeworld> map(CharacterListDto listDto) {
        Map<String, MysqlHomeworld> homeworldMap = new HashMap<>();
        for (CharacterDto characterDto : listDto.getCharacters()) {
            homeworldMap.computeIfAbsent(characterDto.getHomeworld(), v -> new MysqlHomeworld()).setName(characterDto.getHomeworld());
            MysqlCharacter mysqlCharacter = getSwCharacter(characterDto, homeworldMap);
            homeworldMap.get(characterDto.getHomeworld()).getCharacters().add(mysqlCharacter);
        }
        return new ArrayList<>(homeworldMap.values());
    }

    private static MysqlCharacter getSwCharacter(CharacterDto characterDto, Map<String, MysqlHomeworld> homeworldMap) {
        return MysqlCharacter.builder()
                .name(characterDto.getName())
                .pictureUrl(characterDto.getPic())
                .homeworld(homeworldMap.get(characterDto.getHomeworld()))
                .build();
    }

    public static List<MonogCharacter> mysqlToMongo(List<MysqlCharacter> mysqlCharacters) {
        return mysqlCharacters
                .stream()
                .map(v -> MonogCharacter.builder()
                        .name(v.getName())
                        .pictureUrl(v.getPictureUrl())
                        .homeworld(v.getHomeworld().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
