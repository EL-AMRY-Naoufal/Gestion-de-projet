package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.GroupeService;
import com.fst.il.m2.Projet.dto.GroupeDto;
import com.fst.il.m2.Projet.mapper.GroupeMapper;
import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
@Tag(name = "Groupe Controller", description = "Gestion des groupes")
public class GroupeController {

    private final GroupeService groupeService;

    @Autowired
    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @Operation(summary = "Créer un nouveau groupe", description = "Cette méthode permet de créer un nouveau groupe.")
    @ApiResponse(responseCode = "201", description = "Groupe créé avec succès")
    // Create a new Groupe
    @PostMapping
    public ResponseEntity<GroupeDto> createGroupe(@RequestBody GroupeDto groupeDto) {
        Groupe savedGroupe = groupeService.saveGroupe(GroupeMapper.toEntity(groupeDto));
        return new ResponseEntity<>(GroupeMapper.toDto(savedGroupe), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer tous les groupes", description = "Cette méthode permet de récupérer la liste de tous les groupes.")
    @ApiResponse(responseCode = "200", description = "Liste des groupes récupérée")
    @GetMapping
    public ResponseEntity<List<GroupeDto>> getAllGroupes() {
        List<Groupe> groupes = groupeService.getAllGroupes();
        return new ResponseEntity<>(groupes.stream().map(GroupeMapper::toDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un groupe par ID", description = "Cette méthode permet de récupérer un groupe par son ID.")
    @ApiResponse(responseCode = "200", description = "Groupe récupéré avec succès")
    @ApiResponse(responseCode = "404", description = "Groupe non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<GroupeDto> getGroupeById(@PathVariable Long id) {
        try {
            Groupe groupe = groupeService.getGroupeById(id);
            GroupeDto groupeDto = GroupeMapper.toDto(groupe);
            return new ResponseEntity<>(groupeDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer les groupes par ID de module", description = "Cette méthode permet de récupérer les groupes par l'ID du module.")
    @ApiResponse(responseCode = "200", description = "Groupes récupérés avec succès")
    @GetMapping("/module/{moduleId}")
    public List<GroupeDto> getGroupesByModuleId(@PathVariable Long moduleId) {
        List<Groupe> groupes = groupeService.getGroupesByModule(Module.builder().id(moduleId).build());
        return groupes.stream().map(GroupeMapper::toDto).toList();
    }


    @Operation(summary = "Supprimer un groupe", description = "Cette méthode permet de supprimer un groupe par son ID.")
    @ApiResponse(responseCode = "204", description = "Groupe supprimé avec succès")
    @ApiResponse(responseCode = "406", description = "Groupe ne peut pas être supprimé")
    // Delete a Groupe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        if(!groupeService.hasAffectations(id)) {
            groupeService.deleteGroupe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
