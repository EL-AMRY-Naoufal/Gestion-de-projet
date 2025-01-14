package com.fst.il.m2.Projet.mapper;

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
    }

    public static UserRequest.UserDto userToUserDto(User user){
        return UserRequest.UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(
                        userRole -> UserRoleDto.builder()
                                .role(userRole.getRole())
                                .year(userRole.getYear())
                                .build()
                ).toList())
                .build();
    }

     */
}
