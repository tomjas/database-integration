package com.database.integration.mysql.importer.dto;

import lombok.Data;

@Data
public class CharacterDto {
    private long id;
    private String name;
    private String pic;
    private String homeworld;
}
