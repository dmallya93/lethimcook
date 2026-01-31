package com.lethimcook.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point for LetHimCook CLI.
 * <p>
 * This application provides a command-line interface for unit conversions
 * using Spring Shell.
 */
@SpringBootApplication
public class CliApplication {

    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }
}
