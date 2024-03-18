package com.database.integration.mongodb.repository;

import com.database.integration.mongodb.model.MonogCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCharacterRepository extends MongoRepository<MonogCharacter, String> {
}
