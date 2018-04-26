package com.example.firstwebapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Projects {

    @RequestMapping("/landing")
    public String displayProjects() {
        return "landing";
    }
}
