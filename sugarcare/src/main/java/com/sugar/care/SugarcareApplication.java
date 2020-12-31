package com.sugar.care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SugarcareApplication {

    public static void main(String[] args) {

        SpringApplication.run(SugarcareApplication.class, args);
    }

}
