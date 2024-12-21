package com.shad.JobPortal.controller;


import com.shad.JobPortal.entity.Application;
import com.shad.JobPortal.entity.Job;
import com.shad.JobPortal.entity.User;
import com.shad.JobPortal.repository.ApplicationRepository;
import com.shad.JobPortal.repository.JobRepository;
import com.shad.JobPortal.repository.UserRepository;
import com.shad.JobPortal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("user/all-jobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        List<Job> jobs = jobRepository.findAll();
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("user/application")
    public ResponseEntity<String> createApplication(@RequestBody Application application ){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            System.out.println(userName); // userName i have set to email
            User user = userRepository.findByEmail(userName);

            Application saved = applicationRepository.save(application);
            List<Application> applicationListList = user.getApplicationListList();
            applicationListList.add(saved);
            user.setApplicationListList(applicationListList);
            userRepository.save(user);

            return ResponseEntity.ok().body("Created a new Application");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }


}
