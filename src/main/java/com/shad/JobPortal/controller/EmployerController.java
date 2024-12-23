package com.shad.JobPortal.controller;

import com.shad.JobPortal.dto.StatusUpdateDTO;
import com.shad.JobPortal.entity.Application;
import com.shad.JobPortal.entity.Job;
import com.shad.JobPortal.repository.ApplicationRepository;
import com.shad.JobPortal.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class EmployerController {


    @Autowired
    JobRepository jobRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @PostMapping("employer/job")
    public ResponseEntity<String> createNewJob(@RequestBody Job job){
        jobRepository.save(job);
        return ResponseEntity.ok("Job created");
    }

    @GetMapping("employer/jobs/{empId}")
    public ResponseEntity<List<Job>> getAllJobs(@PathVariable String empId){
         List<Job> totalJobs=jobRepository.findAll();
         List<Job> jobs=new ArrayList<>();
         for(Job job:totalJobs){
             if(job.getEmpId().equals(empId)){
                 jobs.add(job);
             }
         }

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("employer/jobs/{id}/applications")
    public ResponseEntity<List<Application>> getAllApplications(@PathVariable String id){
        try{
            List<Application> applicationList = applicationRepository.findAll();
            List<Application> applications = new ArrayList<>();
            for (Application application : applicationList) {

                if (id.equals(application.getJobId())) {

                    applications.add(application);
                }
            }
            return ResponseEntity.ok(applications);
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("employer/job")
    public String updateJob(@RequestBody Job job){
        Optional<Job> jobFromDb = jobRepository.findById(job.getId());
        if(jobFromDb.isPresent()){
            Job existingJob = jobFromDb.get();

            existingJob.setJobTitle(job.getJobTitle());
            existingJob.setJobDescription(job.getJobDescription());
            existingJob.setJobLocation(job.getJobLocation());
            jobRepository.save(existingJob);
            return "Job updated successfully";
        } else {
            return "Job not found with ID: " + job.getId();

        }

    }

    @DeleteMapping("employer/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable ObjectId id){
        jobRepository.deleteById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @PutMapping("employer/applications/status/{id}")
    public String updateApplication(@PathVariable ObjectId id, @RequestBody StatusUpdateDTO statusUpdateDTO) {
        Optional<Application> applicationFromDb = applicationRepository.findById(id);
        if (applicationFromDb.isPresent()) {
            Application existingApplication = applicationFromDb.get();
            existingApplication.setStatus(statusUpdateDTO.getStatus()); // Extract the status value
            applicationRepository.save(existingApplication);
            return "Application status updated successfully";
        } else {
            return "Application not found with ID: " + id;
        }
    }


}
