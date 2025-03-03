package com.fst.il.m2.Projet.models;

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
public class HeuresAssignees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double heures;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "annee_id", nullable = false)
    private Annee annee;
}
