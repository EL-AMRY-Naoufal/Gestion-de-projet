package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.GroupeService;
import com.fst.il.m2.Projet.dto.GroupeDto;
import com.fst.il.m2.Projet.mapper.GroupeMapper;
import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

    private final GroupeService groupeService;

    @Autowired
    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    // Create a new Groupe
    @PostMapping
    public ResponseEntity<GroupeDto> createGroupe(@RequestBody GroupeDto groupeDto) {
        Groupe savedGroupe = groupeService.saveGroupe(GroupeMapper.toEntity(groupeDto));
        return new ResponseEntity<>(GroupeMapper.toDto(savedGroupe), HttpStatus.CREATED);
    }

    // Get all Groupes
    @GetMapping
    public ResponseEntity<List<GroupeDto>> getAllGroupes() {
        List<Groupe> groupes = groupeService.getAllGroupes();
        return new ResponseEntity<>(groupes.stream().map(GroupeMapper::toDto).toList(), HttpStatus.OK);
    }

    // Get a Groupe by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        try {
            Groupe groupe = groupeService.getGroupeById(id);
            return new ResponseEntity<>(groupe, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/module/{moduleId}")
    public List<Groupe> getGroupesByModuleId(@PathVariable Long moduleId) {
        List<Groupe> groupes = groupeService.getGroupesByModule(Module.builder().id(moduleId).build());

        //emptying "module" so the json is not too deep

        groupes.forEach(g -> {
            g.setModule(null);
            g.getAffectations().forEach(a -> {
                a.setGroupe(null);
                a.getEnseignant().setAffectations(null);
            });
        });

        return groupes;
    }

    // Delete a Groupe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        try {
            groupeService.deleteGroupe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
