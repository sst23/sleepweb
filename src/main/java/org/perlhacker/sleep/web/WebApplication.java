package org.perlhacker.sleep.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class WebApplication {
    // TODO: resolve this via command line arguments
    private static final File DEFAULT_FILE = new File(System.getProperty("user.home"), "Documents/schlaf.txt");

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean("input")
    public File getInputLocation() {
        return DEFAULT_FILE;
    }

    @Bean("output")
    public File getOutputLocation() {
        return DEFAULT_FILE;
    }
}