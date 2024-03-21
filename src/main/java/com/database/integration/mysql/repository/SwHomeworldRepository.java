package com.database.integration.mysql.repository;

import com.database.integration.mysql.model.SwHomeworld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwHomeworldRepository extends JpaRepository<SwHomeworld, Long> {

    Optional<SwHomeworld> findByName(String name);
}
