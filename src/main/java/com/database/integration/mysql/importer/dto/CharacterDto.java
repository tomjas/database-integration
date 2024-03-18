package com.database.integration.mysql.importer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CharacterDto {
    @JsonIgnore
    private long id;

    @NotBlank(message = "Name of the character cannot be empty")
    private String name;

    private String pic;
    private String homeworld;
}
