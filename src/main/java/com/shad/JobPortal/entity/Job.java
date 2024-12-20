package com.shad.JobPortal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    private String id;
    private String compName;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String empId;


}
