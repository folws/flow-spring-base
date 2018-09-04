package com.itsfolarin.starter.springflowbase.backend;

import com.itsfolarin.starter.springflowbase.backend.security.AuthConfiguration;
import com.itsfolarin.starter.springflowbase.backend.security.AuthProperties;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableConfigurationProperties({AuthProperties.class})
@EnableMongoRepositories
@Import({AuthConfiguration.class, MongoAutoConfiguration.class})
public class AppConfiguration {
}
