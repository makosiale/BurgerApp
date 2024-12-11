package ru.osipovmaksim.BurgerApp.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class LiquibaseConfig {
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/db/changelog");
        return liquibase;
    }
}
