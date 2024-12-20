package com.shad.JobPortal.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("user/all-jobs")
    public String getAllJobs(){
        return "All Jobs";
    }

    @PostMapping("user/application")
    public String createApplication(){
        return "Create Application";
    }


}
