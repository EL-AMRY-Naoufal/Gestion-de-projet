package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.User;

public class UserRequest {
    private User user;
    private Long responsableId;

    public UserRequest() {
    }

    private UserRequest(Builder builder) {
        this.user = builder.user;
        this.responsableId = builder.responsableId;
    }

    public User getUser() {
        return user;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public static class Builder {
        private User user;
        private Long responsableId;

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setResponsableId(Long responsableId) {
            this.responsableId = responsableId;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "user=" + user +
                ", responsableId=" + responsableId +
                '}';
    }
}
