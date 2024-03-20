package com.database.integration.mysql.importer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImporterStartup implements CommandLineRunner {

    private final ImporterService importerService;

    @Override
    public void run(String... args) throws Exception {
        importerService.persist();
    }
}
