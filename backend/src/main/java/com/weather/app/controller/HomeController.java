package com.weather.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HomeController {
    @GetMapping
    public String getString(){
        return "Hello Controller";
    }
}
