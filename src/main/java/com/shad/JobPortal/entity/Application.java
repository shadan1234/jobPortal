package com.shad.JobPortal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    private ObjectId id;
    private String name;
    private String description;
    private String resumeUrl;

    private String userId;

    private String jobId;
    private String status;
}
