package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.mapper.EnseignantMapper;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseigants")
@RequiredArgsConstructor
public class EnseignantController {
    private final EnseignantService enseignantService;
    @GetMapping("/enseignants-non-enregistres")
    public ResponseEntity<List<User>> getEnseignantsNotInEnseignantTable() {
        List<User> users = enseignantService.getUsersWithRoleEnseignantNotInEnseignant();
        return ResponseEntity.ok(users);
    }

    @PostMapping()
    public EnseignantDto createEnseignant(@RequestBody EnseignantDto enseignant) {
        return EnseignantMapper.enseignantToEnseignantDto(this.enseignantService.createEnseignant(enseignant.getId()
        , enseignant.getMaxHeuresService(), enseignant.getHeuresAssignees(), enseignant.getCategorie()));
    }

    @GetMapping("{id}")
    public EnseignantDto getEnseignantById(@PathVariable long id) {
        return EnseignantMapper.enseignantToEnseignantDto(enseignantService.getEnseignantById(id));
    }

    @PutMapping()
    public  EnseignantDto updateEnseignant(@RequestBody EnseignantDto enseignant) {
        return EnseignantMapper.enseignantToEnseignantDto(
                this.enseignantService.updateEnseignant(
                        enseignant.getId(),
                        enseignant.getMaxHeuresService(),
                        enseignant.getCategorie()
                )
        );
    }
}
