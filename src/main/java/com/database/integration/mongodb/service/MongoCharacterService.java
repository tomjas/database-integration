package com.database.integration.mongodb.service;

import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.repository.MongoCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MongoCharacterService {

    private final MongoCharacterRepository characterRepository;

    public List<MonogCharacter> getCharacters() {
        return characterRepository.findAll();
    }
}
