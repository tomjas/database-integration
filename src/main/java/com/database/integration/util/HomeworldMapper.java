package com.database.integration.util;

import com.database.integration.mysql.model.SwHomeworld;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HomeworldMapper {

    HomeworldMapper INSTANCE = Mappers.getMapper(HomeworldMapper.class);

    @Mapping(source = "string", target = "name")
    SwHomeworld map(String string);
}
