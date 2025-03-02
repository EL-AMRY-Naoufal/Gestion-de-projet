package com.fst.il.m2.Projet.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fst.il.m2.Projet.serializers.AnneeDeserializer;
import com.fst.il.m2.Projet.serializers.AnneeIdSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Annee")
@JsonSerialize(using = AnneeIdSerializer.class)
@JsonDeserialize(using = AnneeDeserializer.class)
public class Annee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int debut;

    @OneToMany(mappedBy = "annee")
    private List<Departement> departements;

}
