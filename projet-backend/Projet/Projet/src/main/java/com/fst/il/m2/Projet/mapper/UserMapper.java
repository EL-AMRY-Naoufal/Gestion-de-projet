package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.dto.UserRoleDto;
import com.fst.il.m2.Projet.models.User;

public class UserMapper {

    // A corriger car supp da la classe UserGetAllDto
    /*
    public static UserGetAllDto userToUserGetAllDto(User user) {
        return UserGetAllDto.builder()
                .id(user.getId())
                .roles(user.getRoles().stream().map(UserRole::getRole).toList())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }*/

    public static UserRequest.UserDto userToUserDto(User user){
        return UserRequest.UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(
                        userRole -> UserRoleDto.builder()
                                .role(userRole.getRole())
                                .year(userRole.getYear().getId())
                                .build()
                ).toList())
                .build();
    }


}
