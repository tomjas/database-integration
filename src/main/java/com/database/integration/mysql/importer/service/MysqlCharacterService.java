package com.database.integration.mysql.importer.service;

import com.database.integration.kafka.service.Producer;
import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.repository.MongoCharacterRepository;
import com.database.integration.mysql.importer.dto.CharacterDto;
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
    private final MongoCharacterRepository mongoCharacterRepository;
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
        characterRepository.save(mysqlCharacter);

        //TODO mongodb integration by kafka cosumer in separate project
        MonogCharacter mongoCharacter = DataMapper.mysqlToMongo(mysqlCharacter);
        mongoCharacterRepository.save(mongoCharacter);

        kafkaProducer.send(mongoCharacter);

        return mysqlCharacter;
    }
}
