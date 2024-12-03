package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Entity
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
    @Column(name = "role")        // The column for the role
    @Enumerated(EnumType.STRING)  // Ensure the Role enum is stored as a string
    private Map<Integer, Role> roles = new HashMap<>();

    // Default constructor
    public User() {
    }

    // Constructor with roles map
    public User(Long id, String username, String password, String email, Map<Integer, Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    // Constructor for creating a user with roles for specific years
    public User(String username, String password, String email, Map<Integer, Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Integer, Role> getRoles() {
        return roles;
    }

    public void setRoles(Map<Integer, Role> roles) {
        this.roles = roles;
    }

    // Add a role for a specific year
    public void addRole(Integer year, Role role) {
        this.roles.put(year, role);
    }

    // Remove a role for a specific year
    public void removeRole(Integer year) {
        this.roles.remove(year);
    }

    // Check if a role exists for a specific year
    public boolean hasRoleForYear(Integer year, Role role) {
        return this.roles.get(year) != null && this.roles.get(year) == role;
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
