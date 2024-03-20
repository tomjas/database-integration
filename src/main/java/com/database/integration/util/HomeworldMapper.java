package com.database.integration.util;

import com.database.integration.mysql.model.MysqlHomeworld;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface HomeworldMapper {

    HomeworldMapper INSTANCE = Mappers.getMapper(HomeworldMapper.class);

    @Mapping(source = "string", target = "name")
    MysqlHomeworld map(String string);

    Set<MysqlHomeworld> map(Set<String> names);
}
