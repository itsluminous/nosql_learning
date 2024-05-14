package com.okmich.movielens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = "com.okmich.movielens")
@EnableNeo4jRepositories(basePackages = "com.okmich.movielens.entity.repo")
//@EnableSwagger2
public class MovielensRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovielensRestApiApplication.class, args);
    }
}
