package com.shad.JobPortal.repository;

import com.shad.JobPortal.entity.Job;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, ObjectId> {
}
