package com.database.integration.mysql.util;

import com.database.integration.mysql.importer.SwCharacterDto;
import com.database.integration.mysql.importer.SwCharacterListDto;
import com.database.integration.mysql.model.Homeworld;
import com.database.integration.mysql.model.SwCharacter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataMapper {

    public static List<Homeworld> map(SwCharacterListDto listDto) {
        Map<String, Homeworld> homeworldMap = new HashMap<>();
        for (SwCharacterDto swCharacterDto : listDto.getCharacters()) {
            homeworldMap.computeIfAbsent(swCharacterDto.getHomeworld(), v -> new Homeworld()).setName(swCharacterDto.getHomeworld());
            SwCharacter swCharacter = SwCharacter.builder()
                    .name(swCharacterDto.getName())
                    .pictureUrl(swCharacterDto.getPic())
                    .homeworld(homeworldMap.get(swCharacterDto.getHomeworld()))
                    .build();
            homeworldMap.get(swCharacterDto.getHomeworld()).getSwCharacters().add(swCharacter);
        }
        return homeworldMap.entrySet().stream().map(v -> v.getValue()).collect(Collectors.toList());
    }

}
