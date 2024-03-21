package com.database.integration.mysql.repository;

import com.database.integration.mysql.model.MysqlCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MysqlCharacterRepository extends JpaRepository<MysqlCharacter, Long> {
    Optional<MysqlCharacter> findById(Long id);

    Optional<MysqlCharacter> findByName(String name);
}
