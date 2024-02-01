package com.ssafy.togeballchatting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class TogeballChattingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TogeballChattingApplication.class, args);
    }

}
