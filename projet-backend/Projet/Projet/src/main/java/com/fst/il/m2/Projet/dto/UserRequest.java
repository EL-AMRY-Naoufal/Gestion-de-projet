package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.User;

public class UserRequest {
    private User user;
    private Long responsableId;
    private boolean associateEnseignantWithUser; // New field

    public UserRequest() {
    }

    private UserRequest(Builder builder) {
        this.user = builder.user;
        this.responsableId = builder.responsableId;
        this.associateEnseignantWithUser = builder.associateEnseignantWithUser;
    }

    public User getUser() {
        return user;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public boolean isAssociateEnseignantWithUser() {
        return associateEnseignantWithUser;
    }

    public static class Builder {
        private User user;
        private Long responsableId;
        private boolean associateEnseignantWithUser; // Builder field

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setResponsableId(Long responsableId) {
            this.responsableId = responsableId;
            return this;
        }

        public Builder setAssociateEnseignantWithUser(boolean associateEnseignantWithUser) {
            this.associateEnseignantWithUser = associateEnseignantWithUser;
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
                ", associateEnseignantWithUser=" + associateEnseignantWithUser +
                '}';
    }
}
