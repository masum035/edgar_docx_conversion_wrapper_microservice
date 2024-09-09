package com.wsd.edgardocswrapper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class DocxController {

    private static final Logger logger = LoggerFactory.getLogger(DocxController.class);
    
    @GetMapping("/health")
    public String HealthCheck(){
        logger.warn("Task performed successfully.");
        return "My App is running!";
    }
}
