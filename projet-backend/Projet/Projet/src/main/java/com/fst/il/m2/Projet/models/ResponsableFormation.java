package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ResponsableFormation")
public class ResponsableFormation {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ResponsableFormation() {
    }

    public ResponsableFormation(Long id, User user) {
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
