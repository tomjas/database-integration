package com.database.integration.mongodb.service;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.repository.MongoCharacterRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MongoCharacterService {

    private MongoCharacterRepository characterRepository;

    public List<MonogCharacter> getCharacters() {
        return characterRepository.findAll();
    }
}
