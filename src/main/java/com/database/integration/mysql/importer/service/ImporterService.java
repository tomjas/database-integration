package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.SwCharacterListDto;
import com.database.integration.mysql.model.Homeworld;
import com.database.integration.mysql.repository.MysqlHomeworldRepository;
import com.database.integration.mysql.util.DataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ImporterService {

    private DataReader reader;
    private MysqlHomeworldRepository mysqlHomeworldRepository;

    @Transactional
    public void persist() throws IOException {
        SwCharacterListDto data = reader.read();
        List<Homeworld> homeworlds = DataMapper.map(data);
        mysqlHomeworldRepository.saveAll(homeworlds);
        log.debug("Imported {} homewrolds with {} Star Wars characters", homeworlds.size(), homeworlds.stream()
                .flatMap(v -> v.getCharacters().stream()).toList().size());
    }
}
