package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.TypeHeure;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Setter
@Getter
//@Builder
@Entity
@Table(name = "Groupe")
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private int totalHeuresDuGroupe;

    @Setter
    private int heuresAffectees;


    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING) // This will store the enum as a string
    @Column(name = "typeHeure", nullable = false)
    private TypeHeure type;

    @OneToMany(mappedBy = "groupe")
    private List<Affectation> affectations;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;



    public Groupe() {
    }

    public Groupe(Long id, String nom, Date date, TypeHeure type, Module module,int totalHeuresDuGroupe, List<Affectation> affectations) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.type = type;
        this.module = module;
        this.affectations = affectations;
        this.totalHeuresDuGroupe = totalHeuresDuGroupe;
        this.heuresAffectees = 0;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
        for (Affectation a : affectations) {
            this.heuresAffectees += a.getHeuresAssignees();
        }
    }

}
