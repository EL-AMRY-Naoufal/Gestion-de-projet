package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String message;
    private UserRequest.UserDto user;
    private Long currentYearId;
}