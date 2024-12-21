package com.shad.JobPortal.repository;

import com.shad.JobPortal.entity.Application;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<Application, ObjectId> {
}
