package ru.crm.taskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ConstructionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstructionsApplication.class, args);
    }
}
