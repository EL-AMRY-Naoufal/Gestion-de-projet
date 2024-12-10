package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserRequest {
    private User user;
    private Long responsableId;
    private boolean associateEnseignantWithUser; // New field

    private Long yearId;

    @Override
    public String toString() {
        return "UserRequest{" +
                "user=" + user +
                ", responsableId=" + responsableId +
                ", associateEnseignantWithUser=" + associateEnseignantWithUser +
                '}';
    }
}
