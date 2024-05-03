package com.example.onlinelearningplatform.controllers;

import com.example.onlinelearningplatform.models.User;
import com.example.onlinelearningplatform.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;

    @GetMapping("/main")
    public String forMain(){
        return "main";
    }

    @GetMapping("/home")
    public String home(){ //secured
        return "home";
    }

    @GetMapping("/info")
    public String userDate(Principal principal){
        return principal.getName();
    }
}
