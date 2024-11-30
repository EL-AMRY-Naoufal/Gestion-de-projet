package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.UserGetAllDto;
import com.fst.il.m2.Projet.models.User;

public class UserMapper {
    public static UserGetAllDto userToUserGetAllDto(User user) {
        return UserGetAllDto.builder()
                .id(user.getId())
                .roles(user.getRoles())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
