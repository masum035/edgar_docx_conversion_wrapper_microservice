package com.wsd.docx_conversion_wrapper_microservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DocxConversionWrapperApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DocxConversionWrapperApplication.class, args);
    }

}
