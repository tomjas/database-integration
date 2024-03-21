package com.database.integration.util;

import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.importer.dto.SwCharacterOutDto;
import com.database.integration.mysql.model.SwCharacter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SwCharacterMapper {

    SwCharacterMapper INSTANCE = Mappers.getMapper(SwCharacterMapper.class);

    @Mapping(source = "pic", target = "pictureUrl")
    @Mapping(target = "homeworld", ignore = true)
    @Mapping(target = "id", ignore = true)
    SwCharacter inDtoToSwCharacter(SwCharacterInDto dto);

    @Mapping(source = "character.homeworld.name", target = "homeworld")
    @Mapping(target = "mysqlId", source = "id")
    SwCharacterOutDto swCharacterToOutDto(SwCharacter character);

    List<SwCharacterOutDto> swCharacterToOutDto(List<SwCharacter> swCharacters);
}
