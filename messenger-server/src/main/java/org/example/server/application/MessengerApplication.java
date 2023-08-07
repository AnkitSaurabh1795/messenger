package org.example.server.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class MessengerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);
    }
}
