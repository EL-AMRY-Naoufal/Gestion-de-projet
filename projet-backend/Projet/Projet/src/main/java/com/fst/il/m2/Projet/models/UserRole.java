package com.fst.il.m2.Projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"Role", "Year", "User_id"})}
)
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="Role")
    private Role role;
    @Column(name="Year")
    private Long year;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                ", year=" + year +
                ", user=" + user.getId() +
                '}';
    }
}
