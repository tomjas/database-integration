package com.database.integration.mongodb.service;


import com.database.integration.mongodb.model.MonogCharacter;
import com.database.integration.mongodb.repository.MongoCharacterRepository;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
import com.database.integration.util.DataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class IntegrationService {

    private MysqlCharacterRepository mysqlRepository;
    private MongoCharacterRepository mongodbRepository;

    public void transfer() {
        List<MysqlCharacter> mysqlCharacters = mysqlRepository.findAll();
        List<MonogCharacter> monogCharacters = DataMapper.mysqlToMongo(mysqlCharacters);
        mongodbRepository.saveAll(monogCharacters);
    }

}
