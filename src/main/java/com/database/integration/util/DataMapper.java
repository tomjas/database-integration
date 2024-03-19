package com.database.integration.util;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataMapper {

    public static List<MysqlHomeworld> map(CharacterListDto listDto) {
        Map<String, MysqlHomeworld> homeworldMap = new HashMap<>();
        for (CharacterDto characterDto : listDto.characters()) {
            homeworldMap.computeIfAbsent(characterDto.homeworld(), v -> new MysqlHomeworld()).setName(characterDto.homeworld());
            MysqlCharacter mysqlCharacter = map(characterDto, homeworldMap.get(characterDto.homeworld()));
            homeworldMap.get(characterDto.homeworld()).getCharacters().add(mysqlCharacter);
        }
        return new ArrayList<>(homeworldMap.values());
    }

    public static MysqlCharacter map(CharacterDto dto) {
        return map(dto, null);
    }

    private static MysqlCharacter map(CharacterDto characterDto, MysqlHomeworld homeworld) {
        MysqlCharacter result = CharacterMapper.INSTANCE.characterDtoToMysql(characterDto);
        if (Objects.isNull(homeworld)) {
            homeworld = HomeworldMapper.INSTANCE.map(characterDto.homeworld());
        }
        result.setHomeworld(homeworld);
        return result;
    }

    public static List<MonogCharacter> mysqlToMongo(List<MysqlCharacter> mysqlCharacters) {
        return CharacterMapper.INSTANCE.mysqlToMongo(mysqlCharacters);
    }

    public static MonogCharacter mysqlToMongo(MysqlCharacter mysqlCharacter) {
        return CharacterMapper.INSTANCE.mysqlToMongo(mysqlCharacter);
    }

}
