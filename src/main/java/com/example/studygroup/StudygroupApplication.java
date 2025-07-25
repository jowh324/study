package com.example.studygroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudygroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudygroupApplication.class, args);
    }

}
