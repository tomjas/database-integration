package com.database.integration.kafka.service;

import com.database.integration.mysql.importer.dto.SwCharacterOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, SwCharacterOutDto> template;

    public void send(SwCharacterOutDto swCharacterOutDto) {
        template.send(topic, swCharacterOutDto);
    }
}
