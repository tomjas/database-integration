package com.database.integration.mysql.importer.service;

import com.database.integration.mongodb.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImporterStartup implements CommandLineRunner {

    private final ImporterService importerService;
    private final IntegrationService integrationService;

    @Override
    public void run(String... args) throws Exception {
        importerService.persist();
        integrationService.transfer();
    }
}
