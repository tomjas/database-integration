package com.database.integration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DatabaseIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseIntegrationApplication.class, args);
	}

	@Component
	public static class BaselineOnMigrateMigrationStrategy implements FlywayMigrationStrategy {

		@Override
		public void migrate(Flyway flyway) {
			flyway.migrate();
		}
	}

}
