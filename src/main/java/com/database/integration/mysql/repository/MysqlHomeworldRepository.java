package com.database.integration.mysql.repository;

import com.database.integration.mysql.model.MysqlHomeworld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MysqlHomeworldRepository extends JpaRepository<MysqlHomeworld, Long> {

    Optional<MysqlHomeworld> findByName(String name);
}
