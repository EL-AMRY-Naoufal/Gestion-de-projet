package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserRoleDto {
    private Long yearId;
    private Role role;

    public UserRole toUserRole(){
        return UserRole.builder()
                .year(yearId)
                .role(role)
                .build();
    }
}
