package com.database.integration.kafka.service;

import com.database.integration.mysql.importer.dto.MonogCharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    @Value("${kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, MonogCharacterDto> template;

    public void send(MonogCharacterDto monogCharacterDto) {
        template.send(topic, monogCharacterDto);
    }
}
