package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.TypeHeure;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Groupe")
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING) // This will store the enum as a string
    @Column(name = "typeHeure", nullable = false)
    private TypeHeure type;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public Groupe() {
    }

    public Groupe(Long id, String nom, Date date, TypeHeure type, Module module) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.type = type;
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TypeHeure getType() {
        return type;
    }

    public void setType(TypeHeure type) {
        this.type = type;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
