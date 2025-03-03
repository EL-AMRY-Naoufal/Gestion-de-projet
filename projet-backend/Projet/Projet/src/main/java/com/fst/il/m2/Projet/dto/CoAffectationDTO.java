package com.fst.il.m2.Projet.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class CoAffectationDTO {


    private Long id;
    private String enseignantName;
    private String enseignantFirstName;
    private String groupeName;
    private int heuresAssignees;
    private String dateAffectation;

}
