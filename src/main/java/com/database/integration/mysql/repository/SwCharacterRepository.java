package com.database.integration.mysql.repository;

import com.database.integration.mysql.model.SwCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwCharacterRepository extends JpaRepository<SwCharacter, Long> {
    Optional<SwCharacter> findById(Long id);

    Optional<SwCharacter> findByName(String name);
}
