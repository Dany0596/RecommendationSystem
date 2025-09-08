package com.recommendation_system.mvc.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String   userId;
   
    /**
     * Default constructor.
     */
    public User() {
        this.userId   = null;
    }
    
    /*  DEFAULT GETTERS AND SETTERS */

    public String getUserId() {
        return userId;
    }  

    public void setUserId(String userId) {
        this.userId = userId;
    }
}