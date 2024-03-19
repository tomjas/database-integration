package com.database.integration.util;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(source = "pic", target = "pictureUrl")
    @Mapping(target = "homeworld", ignore = true)
    @Mapping(target = "id", ignore = true)
    MysqlCharacter characterDtoToMysql(CharacterDto dto);

    @Mapping(source = "character.homeworld.name", target = "homeworld")
    @Mapping(target = "id", ignore = true)
    MonogCharacter mysqlToMongo(MysqlCharacter character);
}
