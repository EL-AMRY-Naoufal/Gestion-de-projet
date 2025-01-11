package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
@Table(name = "ResponsableDepartement")
public class ResponsableDepartement{

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ResponsableDepartement() {
    }

    public ResponsableDepartement(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
