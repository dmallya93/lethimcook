package com.lethimcook.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Command-line interface for LetHimCook.
 * This is a minimal stub that will be fully implemented in Milestone 2.
 */
@SpringBootApplication
public class LetHimCookCli implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LetHimCookCli.class, args);
    }

    @Override
    public void run(String... args) {
        // Will be implemented in Milestone 2
        System.out.println("LetHimCook CLI - To be implemented");
    }
}
