package com.database.integration.util;

import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.importer.dto.SwCharacterWrapperDto;
import com.database.integration.mysql.importer.dto.SwCharacterOutDto;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.model.SwCharacter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataMapper {

    public static Set<SwHomeworld> getHomeworlds(SwCharacterWrapperDto wrapperDto) {
        Set<String> names = wrapperDto.characters().stream().map(SwCharacterInDto::homeworld).collect(Collectors.toSet());
        return names.stream().map(DataMapper::getHomeworld).collect(Collectors.toSet());
    }

    public static SwHomeworld getHomeworld(String name) {
        if (!StringUtils.hasText(name)) {
            return new SwHomeworld();
        }
        return HomeworldMapper.INSTANCE.map(name);
    }

    public static SwCharacter map(SwCharacterInDto dto) {
        return map(dto, null);
    }

    public static SwCharacter map(SwCharacterInDto dto, SwHomeworld homeworld) {
        SwCharacter result = SwCharacterMapper.INSTANCE.inDtoToSwCharacter(dto);
        if (Objects.isNull(homeworld)) {
            homeworld = HomeworldMapper.INSTANCE.map(dto.homeworld());
        }
        result.setHomeworld(homeworld);
        return result;
    }

    public static List<SwCharacterOutDto> mysqlToMongo(List<SwCharacter> characters) {
        return SwCharacterMapper.INSTANCE.swCharacterToOutDto(characters);
    }

    public static SwCharacterOutDto mysqlToMongo(SwCharacter character) {
        return SwCharacterMapper.INSTANCE.swCharacterToOutDto(character);
    }

    public static Map<String, SwHomeworld> asMap(Set<SwHomeworld> homeworlds) {
        return homeworlds.stream().collect(Collectors.toMap(SwHomeworld::getName, Function.identity()));
    }

    //TODO test
    public static Set<SwCharacter> asSwCharacters(SwCharacterWrapperDto wrapperDto, Map<String, SwHomeworld> map) {
        return wrapperDto.characters().stream().map(v -> {
            SwCharacter character = SwCharacterMapper.INSTANCE.inDtoToSwCharacter(v);
            character.setHomeworld(map.get(v.homeworld()));
            return character;
        }).collect(Collectors.toSet());
    }
}
