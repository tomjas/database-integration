package com.database.integration.mysql.importer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;

public record CharacterDto(@JsonIgnore long id,
                           @NotBlank(message = "Name of the character cannot be empty") String name, String pic,
                           String homeworld) {
}
