package com.shad.JobPortal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    private String id;
    private String name;
    private String description;
    private String resumeUrl;
    private String userId;
    private String empId;
}
