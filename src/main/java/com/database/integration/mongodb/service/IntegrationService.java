package com.database.integration.mongodb.service;


import com.database.integration.kafka.service.KafkaProducer;
import com.database.integration.mysql.importer.dto.SwCharacterOutDto;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntegrationService {

    private final KafkaProducer producer;

    public void send(List<SwCharacter> swCharacters) {
        List<SwCharacterOutDto> swCharacterOutDtos = DataMapper.mysqlToMongo(swCharacters);
        swCharacterOutDtos.forEach(producer::send);
    }

    public void send(SwCharacter swCharacter) {
        SwCharacterOutDto mongoCharacter = DataMapper.mysqlToMongo(swCharacter);
        producer.send(mongoCharacter);
    }
}
