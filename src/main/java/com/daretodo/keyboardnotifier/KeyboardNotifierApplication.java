package com.daretodo.keyboardnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KeyboardNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyboardNotifierApplication.class, args);
    }

}
