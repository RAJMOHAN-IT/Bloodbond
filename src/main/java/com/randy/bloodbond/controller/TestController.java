package com.randy.bloodbond.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String testApi() {
        return "Backend Connected Successfully!";
    }
}