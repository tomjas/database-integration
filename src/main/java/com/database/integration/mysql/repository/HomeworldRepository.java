package com.database.integration.mysql.repository;

import com.database.integration.mysql.model.Homeworld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworldRepository extends JpaRepository<Homeworld, Long> {
}
