package com.database.integration.mysql.importer.service;

import com.database.integration.kafka.service.Producer;
import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.importer.dto.MonogCharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
import com.database.integration.mysql.repository.MysqlHomeworldRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MysqlCharacterService {

    private final MysqlCharacterRepository characterRepository;
    private final MysqlHomeworldRepository homeworldRepository;
    private final Producer kafkaProducer;

    @Transactional
    public List<MysqlCharacter> getCharacters() {
        return characterRepository.findAll();
    }

    @Transactional
    public List<MysqlHomeworld> getHomeworlds() {
        return homeworldRepository.findAll();
    }

    @Transactional
    public MysqlCharacter add(CharacterDto dto) {
        MysqlCharacter mysqlCharacter = DataMapper.map(dto);
        mysqlCharacter = characterRepository.save(mysqlCharacter);

        MonogCharacterDto mongoCharacter = DataMapper.mysqlToMongo(mysqlCharacter);
        kafkaProducer.send(mongoCharacter);

        return mysqlCharacter;
    }
}
