package com.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalController {

    @GetMapping("/personal/panel")
    public String panelPersonal() {
        
        return "personal/panel";
    }
}
