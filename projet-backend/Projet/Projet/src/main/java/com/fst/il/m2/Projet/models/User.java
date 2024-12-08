package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
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

    // Map to store roles by year
    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "year")  // The key column for the year
    @Column(name = "roles")        // The column for the role
    @Enumerated(EnumType.STRING)  // Ensure the Role enum is stored as a string
    private Map<Long, Role> roles = new HashMap<>();

    // Default constructor
    public User() {
    }

    // Constructor with roles map
    public User(Long id, String username, String password, String email, Map<Long, Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    // Constructor for creating a user with roles for specific years
    public User(String username, String password, String email, Map<Long, Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    // Getters and setters

    // Add a role for a specific year
    public void addRole(Long yearId, Role role) {
        this.roles.put(yearId, role);
    }

    // Remove a role for a specific year
    public void removeRole(Long yearId) {
        this.roles.remove(yearId);
    }

    // Check if a role exists for a specific year
    public boolean hasRoleForYear(Long yearId, Role role) {
        return this.roles.get(yearId) != null && this.roles.get(yearId) == role;
    }

    // Utility method to check if user has a particular role
    public boolean hasRole(Role role) {
        return roles.containsValue(role);
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
