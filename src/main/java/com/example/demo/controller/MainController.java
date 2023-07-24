package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String main() {
        return "Main";
    }
//
//    @GetMapping(value = "/{name}")
//    public String showMain(Model model, @PathVariable("name")String name) {
//        model.addAttribute("name", name);
//        return "Main";
//    }
//
//    @PostMapping(value = "/")
//    public String updateMain(Model model, @RequestParam("test")String name) {
//        model.addAttribute("name", name);
//        return "Main";
//    }

}
