package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
import com.database.integration.mysql.repository.MysqlHomeworldRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MysqlCharacterService {

    private MysqlCharacterRepository characterRepository;
    private MysqlHomeworldRepository homeworldRepository;

    @Transactional
    public List<MysqlCharacter> getCharacters() {
        return characterRepository.findAll();
    }

    @Transactional
    public List<MysqlHomeworld> getHomeworlds() {
        return homeworldRepository.findAll();
    }

}
