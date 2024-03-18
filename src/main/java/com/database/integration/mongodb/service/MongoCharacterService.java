package com.database.integration.mongodb.service;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.repository.MongoCharacterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MongoCharacterService {

    private MongoCharacterRepository characterRepository;

    public List<MonogCharacter> getCharacters() {
        return characterRepository.findAll();
    }
}
