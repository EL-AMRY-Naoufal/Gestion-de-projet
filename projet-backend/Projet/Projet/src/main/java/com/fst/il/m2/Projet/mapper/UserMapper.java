package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.UserGetAllDto;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.models.UserRole;

public class UserMapper {
    public static UserGetAllDto userToUserGetAllDto(User user) {
        return UserGetAllDto.builder()
                .id(user.getId())
                .roles(user.getRoles().stream().map(UserRole::getRole).toList())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
