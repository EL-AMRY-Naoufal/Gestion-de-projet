package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "Departements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany
    @JoinColumn(name = "Formation_ID")
    private List<Formation> formations;

    @ManyToOne
    @JoinColumn(name = "responsable_departement_id")
    private ResponsableDepartement responsableDepartement;

    @ManyToOne
    @JoinColumn(name = "annee_id")
    private Annee annee;
}
