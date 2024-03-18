package com.database.integration.mysql.importer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CharacterDto {
    @JsonIgnore
    private long id;
    private String name;
    private String pic;
    private String homeworld;
}
