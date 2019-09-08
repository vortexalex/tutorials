package com.vortexalex.tutorials.springboot.data.jdbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(basePackages = "com.vortexalex.tutorials.springboot.data.jdbc.persistence")
public class AppConfig extends JdbcConfiguration {

    @Bean(name = "sourceDS")
    @Primary
    @ConfigurationProperties("app.datasource.source")
    public DataSource sourceDatasource() {
        return DataSourceBuilder.create().build();
    }

   /* @Bean
    public NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(sourceDatasource());
    }*/

    @Bean
    public NamingStrategy namingStrategy() {
        return new NamingStrategy() {

            @Override
            public String getTableName(Class<?> type) {
                return "FAKE_TABLE";
            }

            @Override
            public String getQualifiedTableName(Class<?> type) {
                return "FAKE_TABLE";
            }

            @Override
            public String getSchema() {
                return "FAKE_SCHEMA";
            }

        };
    }
}

