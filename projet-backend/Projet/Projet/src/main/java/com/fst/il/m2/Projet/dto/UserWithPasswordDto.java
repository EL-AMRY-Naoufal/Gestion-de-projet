package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithPasswordDto {

    private String username;
    private String name;
    private String firstname;
    private String email;
    private List<UserRoleDto> roles;
    private Long id;
    private String password;

    public User toUser() {
        User user = User.builder()
                .name(name)
                .firstname(firstname)
                .username(username)
                .email(email)
                .id(id)
                .password(password)
                .build();

        user.addUserRoles(roles.stream().map(UserRoleDto::toUserRole).toList());

        return user;
    }
}
