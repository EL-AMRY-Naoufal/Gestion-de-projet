package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.enumurators.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetAllDto {
    private Long id;
    private String username;
    private String email;
    private List<Role> roles;
}
