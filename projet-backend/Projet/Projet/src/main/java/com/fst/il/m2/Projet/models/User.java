package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name="Name")
    private String name;

    @Column(name="Firstname")
    private String firstname;

    @Column(name="Username", unique = true)
    private String username;

    @Column(name="Password")
    private String password;

    @Column(name="Email", unique = true)
    private String email;

    @Builder.Default()
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "UserRole_id")
    private List<UserRole> roles = new ArrayList<>();

    // Getters and setters

    // Add a role for a specific year
    public void addRole(Annee year, Role role) {
        this.roles.add(UserRole.builder().year(year).role(role).user(this).build());
    }

    public void addUserRoles(List<UserRole> userRoles){
        for (UserRole ur : userRoles)
            addRole(ur.getYear(), ur.getRole());
    }

    // Remove a role for a specific year
    public void removeRole(Long yearId, Role role) {
        this.roles = this.roles.stream().filter(userRole -> !userRole.getRole().equals(role) || !userRole.getYear().getId().equals(yearId)).toList();
    }

    // Check if a role exists for a specific year
    public boolean hasRoleForYear(Long yearId, Role role) {
        return roles.stream().anyMatch(userRole -> Objects.equals(userRole.getYear().getId(), yearId) && userRole.getRole() == role);
    }

    // Utility method to check if user has a particular role
    public boolean hasRole(Role role) {
        return roles.stream().anyMatch(userRole -> userRole.getRole().equals(role));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ",firstname='" + firstname + '\'' +
                ",name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
