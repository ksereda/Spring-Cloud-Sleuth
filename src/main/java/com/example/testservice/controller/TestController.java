package com.example.testservice.controller;

import com.example.testservice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
public class TestController {

    Logger logger = Logger.getLogger("TestController");

    @Autowired
    private TestService testService;

    @GetMapping("/start")
    public String start() {
        logger.info("Write some log");
        return "Start page";
    }

    @GetMapping("/test")
    public String checkWork() {
        logger.info("Check service method");
        testService.checkWork();
        return "Success!";
    }

    @GetMapping("/manual")
    public String manualCheckMethodWork() {
        logger.info("Manual check method work");
        testService.manualCheckMethodWork();
        return "Success!";
    }

}
