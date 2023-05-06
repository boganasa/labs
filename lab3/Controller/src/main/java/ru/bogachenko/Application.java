package ru.bogachenko;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.bogachenko.*")
@ComponentScan(basePackageClasses = CatController.class)
@ComponentScan(basePackageClasses = OwnerController.class)
@EntityScan("ru.bogachenko.*")
@EnableJpaRepositories("ru.bogachenko.reposirories")
//@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}