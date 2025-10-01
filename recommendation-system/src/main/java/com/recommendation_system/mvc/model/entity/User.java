package com.recommendation_system.mvc.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
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

    /**
     * Get user ID.
     * 
     * @return ID of the user.
     */
    public String getUserId() {
        return userId;
    }  

    /**
     * Set user ID.
     * 
     * @param userId ID of the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}