package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @Column(name="Username", unique = true)
    private String username;

    @Column(name="Password")
    private String password;

    @Column(name="Email", unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserRole_id")
    private List<UserRole> roles;

    // Getters and setters

    // Add a role for a specific year
    public void addRole(Long yearId, Role role) {
        this.roles.add(UserRole.builder().year(yearId).role(role).build());
    }

    // Remove a role for a specific year
    public void removeRole(Long yearId, Role role) {
        this.roles = this.roles.stream().filter(userRole -> !userRole.getRole().equals(role) || !userRole.getYear().equals(yearId)).toList();
    }

    // Check if a role exists for a specific year
    public boolean hasRoleForYear(Long yearId, Role role) {
        return roles.stream().anyMatch(userRole -> Objects.equals(userRole.getYear(), yearId) && userRole.getRole() == role);
    }

    // Utility method to check if user has a particular role
    public boolean hasRole(Role role) {
        return roles.stream().anyMatch(userRole -> userRole.getRole().equals(role));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
