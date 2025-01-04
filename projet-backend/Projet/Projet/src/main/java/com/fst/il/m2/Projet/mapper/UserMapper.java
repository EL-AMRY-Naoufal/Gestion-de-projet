package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.UserGetAllDto;
import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.dto.UserRoleDto;
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

    public static UserRequest.UserDto userToUserDto(User user){
        return UserRequest.UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(
                        userRole -> UserRoleDto.builder()
                                .role(userRole.getRole())
                                .yearId(userRole.getYear())
                                .build()
                ).toList())
                .build();
    }
}
