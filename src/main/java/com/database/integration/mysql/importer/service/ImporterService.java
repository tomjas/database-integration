package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.repository.MysqlHomeworldRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImporterService {

    private final DataReader reader;
    private final MysqlHomeworldRepository mysqlHomeworldRepository;

    @Transactional
    public void persist() throws IOException {
        CharacterListDto characterListDto = reader.read();
        List<MysqlHomeworld> mysqlHomeworlds = DataMapper.map(characterListDto);
        for (MysqlHomeworld mysqlHomeworld : mysqlHomeworlds) {
            mysqlHomeworldRepository.findByName(mysqlHomeworld.getName()).ifPresent(v -> {
                mysqlHomeworld.setId(v.getId());
                mysqlHomeworld.setCharacters(v.getCharacters());
            });
            mysqlHomeworldRepository.save(mysqlHomeworld);
        }
        log.debug("Imported {} homewrolds with {} Star Wars characters", mysqlHomeworlds.size(), mysqlHomeworlds.stream()
                .flatMap(v -> v.getCharacters().stream()).toList().size());
    }
}
