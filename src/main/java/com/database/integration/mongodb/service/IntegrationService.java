package com.database.integration.mongodb.service;


import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.repository.MongoCharacterRepository;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class IntegrationService {

    private MysqlCharacterRepository mysqlRepository;
    private MongoCharacterRepository mongodbRepository;

    public void transfer() {
        List<MysqlCharacter> characters = mysqlRepository.findAll();
        List<MonogCharacter> characterList = characters
                .stream()
                .map(v -> {
                    MonogCharacter ch = MonogCharacter.builder()
                            .name(v.getName())
                            .pictureUrl(v.getPictureUrl())
                            .homeworld(v.getHomeworld().getName())
                            .build();
                    return ch;
                })
                .collect(Collectors.toList());
        mongodbRepository.saveAll(characterList);
    }
}
