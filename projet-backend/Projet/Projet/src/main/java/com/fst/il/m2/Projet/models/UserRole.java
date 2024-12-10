package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "UserRoles",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"Role", "Year"})}
)
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="Role")
    private Role role;
    @Column(name="Year")
    private Long year;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;
}
