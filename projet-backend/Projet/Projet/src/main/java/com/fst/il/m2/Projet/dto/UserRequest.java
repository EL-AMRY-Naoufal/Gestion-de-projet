package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class UserRequest {
    private UserDto user;
    private Long responsableId;
    private boolean associateEnseignantWithUser; // New field

    private Long year; // New field

    @Override
    public String toString() {
        return "UserRequest{" +
                "user=" + user +
                ", responsableId=" + responsableId +
                ", associateEnseignantWithUser=" + associateEnseignantWithUser +
                '}';
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class UserDto {
        private String username;
        private String name;
        private String firstname;
        private String password;
        private String email;
        private List<UserRoleDto> roles;
        private Long id;


        public User toUser(){
            User user = User.builder()
                    .name(name)
                    .firstname(firstname)
                    .username(username)
                    .email(email)
                    .password(password)
                    .id(id)
                    .build();

            user.addUserRoles(roles.stream().map(UserRoleDto::toUserRole).toList());

            return user;
        }
    }
}
