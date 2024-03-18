package com.database.integration.mysql.importer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SwCharacterListDto {
    private List<SwCharacterDto> characters = new ArrayList<>();
}
