package ru.bogachenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
//@EnableJpaRepositories()
@Configuration
public class ApplicationOwner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationOwner.class, args);
    }
}