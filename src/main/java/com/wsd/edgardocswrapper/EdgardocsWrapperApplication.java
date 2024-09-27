package com.wsd.edgardocswrapper;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EdgardocsWrapperApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EdgardocsWrapperApplication.class, args);
    }

}
