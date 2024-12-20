package com.shad.JobPortal.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class EmployerController {


    @PostMapping("employer/job")
    public String createNewJob(){
        return "New Job";
    }

    @GetMapping("employer/jobs/{empId}")
    public String getAllJobs(@PathVariable String empId){
        return "All Jobs";
    }

    @GetMapping("employer/jobs/{id}/applications")
    public String getAllApplications(@PathVariable int id){
        return "All Applications";
    }

    @PutMapping("employer/jobs/{id}")
    public String updateJob(@PathVariable int id){
        return "Update Job";
    }

    @DeleteMapping("employer/jobs/{id}")
    public String deleteJob(@PathVariable int id){
        return "Delete Job";
    }

    @PutMapping("employer/applications/{id}/status")
    public String updateApplication(@PathVariable int id){
        return "Update Application";
    }

}
