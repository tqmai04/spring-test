package com.example.firstwebapplication.controller;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Project {

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String uploadData() {
        return "create";
    }
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String createProject(@RequestParam String link) {
        Model model = ModelFactory.createDefaultModel();
        model.read(link);

        model.write(System.out);
        return "create";
    }
}
