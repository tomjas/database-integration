package com.database.integration.mysql.importer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ImporterStartup implements CommandLineRunner {

    private DataReader reader;

    @Override
    public void run(String... args) throws Exception {
        SwCharacterListDto data = reader.read();
        log.debug("{}", data);
    }
}
