package com.recommendation_system.mvc.model.entity;

public class User {
    private Long   userId;
   
    /*
     * Default constructor.
     */
    public User() {
        this.userId   = null;
    }
    
    /*  DEFAULT GETTERS AND SETTERS */

    public Long getUserId() {
        return userId;
    }  

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}