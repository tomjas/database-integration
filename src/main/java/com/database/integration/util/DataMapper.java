package com.database.integration.util;

import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.importer.dto.MonogCharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static Set<MysqlHomeworld> getHomeworlds(CharacterListDto listDto) {
        Set<String> names = listDto.characters().stream().map(CharacterDto::homeworld).collect(Collectors.toSet());
        return names.stream().map(DataMapper::getHomeworld).collect(Collectors.toSet());
    }

    public static MysqlHomeworld getHomeworld(String name) {
        if (!StringUtils.hasText(name)) {
            return new MysqlHomeworld();
        }
        return HomeworldMapper.INSTANCE.map(name);
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

    public static List<MonogCharacterDto> mysqlToMongo(List<MysqlCharacter> mysqlCharacters) {
        return CharacterMapper.INSTANCE.mysqlToMongo(mysqlCharacters);
    }

    public static MonogCharacterDto mysqlToMongo(MysqlCharacter mysqlCharacter) {
        return CharacterMapper.INSTANCE.mysqlToMongo(mysqlCharacter);
    }

    //TODO test
    public static Map<String, MysqlHomeworld> asMap(Set<MysqlHomeworld> homeworlds) {
        return homeworlds.stream().collect(Collectors.toMap(MysqlHomeworld::getName, Function.identity()));
    }

    //TODO test
    public static Set<MysqlCharacter> getSwCharacters(CharacterListDto listDto, Map<String, MysqlHomeworld> map) {
        return listDto.characters().stream().map(v -> {
            MysqlCharacter character = CharacterMapper.INSTANCE.characterDtoToMysql(v);
            character.setHomeworld(map.get(v.homeworld()));
            return character;
        }).collect(Collectors.toSet());
    }
}
