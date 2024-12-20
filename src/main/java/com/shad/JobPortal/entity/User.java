package com.shad.JobPortal.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private ObjectId id;
    @NonNull
    private String name;

    @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    @DBRef
    private List<Application> applicationListList = new ArrayList<>();
    private List<String> roles;



}
