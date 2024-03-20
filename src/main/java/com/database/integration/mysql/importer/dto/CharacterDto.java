package com.database.integration.mysql.importer.dto;

import jakarta.validation.constraints.NotBlank;

public record CharacterDto(long id,
                           @NotBlank(message = "Name of the character cannot be empty") String name, String pic,
                           String homeworld) {
}
