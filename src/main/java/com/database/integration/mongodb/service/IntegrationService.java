package com.database.integration.mongodb.service;


import com.database.integration.kafka.service.Producer;
import com.database.integration.mysql.importer.dto.MonogCharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntegrationService {

    private final Producer producer;

    public void send(List<MysqlCharacter> mysqlCharacters) {
        List<MonogCharacterDto> monogCharacterDtos = DataMapper.mysqlToMongo(mysqlCharacters);
        monogCharacterDtos.forEach(producer::send);
    }

    public void send(MysqlCharacter mysqlCharacter) {
        MonogCharacterDto mongoCharacter = DataMapper.mysqlToMongo(mysqlCharacter);
        producer.send(mongoCharacter);
    }
}
