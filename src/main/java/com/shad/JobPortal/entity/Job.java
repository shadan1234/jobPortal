package com.shad.JobPortal.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    private ObjectId id;
    @NotNull
    private String compName;
    @NotNull
    private String jobTitle;
    @NotNull
    private String jobDescription;
    @NotNull
    private String jobLocation;

    private String empId;


}
