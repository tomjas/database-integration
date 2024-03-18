package com.database.integration.mysql.repository;

import com.database.integration.mysql.model.SwCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<SwCharacter, Long> {
}
