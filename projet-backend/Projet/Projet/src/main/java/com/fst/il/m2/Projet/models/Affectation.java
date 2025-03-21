package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Data
@ToString(exclude = "enseignant")
@Table(name = "Affectations")
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heuresAssignees;

    private LocalDate dateAffectation;

    @Column
    private String commentaire = "";

    // Relations
    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
}