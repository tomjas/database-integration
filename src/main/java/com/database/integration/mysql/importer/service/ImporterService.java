package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.SwCharacterListDto;
import com.database.integration.mysql.model.Homeworld;
import com.database.integration.mysql.repository.HomeworldRepository;
import com.database.integration.mysql.util.DataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class ImporterService {

    private DataReader reader;
    private HomeworldRepository homeworldRepository;

    @Transactional
    public void persist() throws IOException {
        SwCharacterListDto data = reader.read();
        List<Homeworld> homeworlds = DataMapper.map(data);
        homeworldRepository.saveAll(homeworlds);
    }
}
