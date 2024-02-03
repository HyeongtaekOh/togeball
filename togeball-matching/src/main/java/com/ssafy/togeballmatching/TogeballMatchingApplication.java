package com.ssafy.togeballmatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@EnableScheduling
@SpringBootApplication
public class TogeballMatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TogeballMatchingApplication.class, args);
    }

}
