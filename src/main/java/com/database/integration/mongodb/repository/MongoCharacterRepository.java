package com.database.integration.mongodb.repository;

import com.database.integration.mongodb.model.MonogCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCharacterRepository extends MongoRepository<MonogCharacter, String> {
}
