package com.fst.il.m2.Projet.dto;


import com.fst.il.m2.Projet.models.User;

public class UserRequest {
    private User user;
    private Long responsableId;

    // Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Long responsableId) {
        this.responsableId = responsableId;
    }
}