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
            MysqlCharacter mysqlCharacter = getSwCharacter(characterDto, homeworldMap.get(characterDto.getHomeworld()));
            homeworldMap.get(characterDto.getHomeworld()).getCharacters().add(mysqlCharacter);
        }
        return new ArrayList<>(homeworldMap.values());
    }

    public static MysqlCharacter map(CharacterDto dto) {
        MysqlHomeworld homeworld = MysqlHomeworld.builder()
                .name(dto.getHomeworld())
                .build();
        return getSwCharacter(dto, homeworld);
    }

    private static MysqlCharacter getSwCharacter(CharacterDto characterDto, MysqlHomeworld homeworld) {
        return MysqlCharacter.builder()
                .name(characterDto.getName())
                .pictureUrl(characterDto.getPic())
                .homeworld(homeworld)
                .build();
    }

    public static List<MonogCharacter> mysqlToMongo(List<MysqlCharacter> mysqlCharacters) {
        return mysqlCharacters
                .stream()
                .map(DataMapper::mysqlToMongo)
                .collect(Collectors.toList());
    }

    public static MonogCharacter mysqlToMongo(MysqlCharacter mysqlCharacter) {
        return MonogCharacter.builder()
                .name(mysqlCharacter.getName())
                .pictureUrl(mysqlCharacter.getPictureUrl())
                .homeworld(mysqlCharacter.getHomeworld().getName())
                .build();
    }

}
