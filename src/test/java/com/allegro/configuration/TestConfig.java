package com.allegro.configuration;

import com.mongodb.client.MongoClient;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
public class TestConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public MongoClient mongoClient() {
        // Create a mock MongoClient for testing
        return mock(MongoClient.class);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        // Create a MongoTemplate bean with the mock MongoClient
        return new MongoTemplate(mongoClient, "test_database");
    }
}
