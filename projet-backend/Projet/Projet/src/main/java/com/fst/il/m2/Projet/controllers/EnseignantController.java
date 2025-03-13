package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.CommentaireDto;
import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.mapper.EnseignantMapper;
import com.fst.il.m2.Projet.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@Validated
@Tag(name = "Enseignant Controller", description = "Gestion des enseignants")
public class EnseignantController {

    @Autowired
    private UserService userService;

    @Autowired
    private EnseignantService enseignantService;

    @Operation(summary = "Récupérer les enseignants non enregistrés", description = "Cette méthode permet de récupérer la liste des utilisateurs avec le rôle enseignant qui ne sont pas enregistrés dans la table des enseignants.")
    @GetMapping("/enseignants-non-enregistres")
    public ResponseEntity<List<User>> getEnseignantsNotInEnseignantTable() {
        List<User> users = enseignantService.getUsersWithRoleEnseignantNotInEnseignant();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Trouver des utilisateurs par nom et prénom", description = "Cette méthode permet de trouver des utilisateurs ayant le même nom et prénom qu'un enseignant.")
    @GetMapping("/finduser")
    public ResponseEntity<List<User>> getUsersWithSameEnseignantNameAndFirstName(
            @RequestParam String name,
            @RequestParam String firstname) {
        List<User> users = userService.findUsersByEnseignantNameAndFirstName(name, firstname);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Récupérer tous les enseignants", description = "Cette méthode permet de récupérer la liste de tous les enseignants.")
    @GetMapping()
    public List<EnseignantDto> getEnseignants(@RequestParam(required = false) String q){
        if(q != null){
            return enseignantService.searchEnseignants(q).stream().map(
                    EnseignantMapper::enseignantToEnseignantDto
            ).collect(Collectors.toList());
        }else{
            return enseignantService.getEnseignants().stream().map(
                    EnseignantMapper::enseignantToEnseignantDto
            ).collect(Collectors.toList());
        }
    }

    @Operation(summary = "Créer un nouvel enseignant", description = "Cette méthode permet de créer un nouvel enseignant.")
    @PostMapping()
    public EnseignantDto createEnseignant(@RequestBody EnseignantDto enseignant) {
        if(enseignant.isHasAccount()) {
            return EnseignantMapper.enseignantToEnseignantDto(this.enseignantService.createEnseignant(
                    enseignant.getUser(),
                    enseignant.getMaxHeuresService(),
                    0,
                    enseignant.getCategorieEnseignant(),
                    enseignant.getNbHeureCategorie(),
                    1L)
            );
        }
        else {
            return EnseignantMapper.enseignantToEnseignantDto(this.enseignantService.createEnseignantWithoutAccount(
                    enseignant.getName(),
                    enseignant.getFirstname(),
                    enseignant.getMaxHeuresService(),
                    0,
                    enseignant.getCategorieEnseignant(),
                    enseignant.getNbHeureCategorie()
            ));
        }
    }

    @Operation(summary = "Récupérer un enseignant par ID", description = "Cette méthode permet de récupérer un enseignant par son ID.")
    @GetMapping("{id}")
    public EnseignantDto getEnseignantById(@PathVariable Long id) {
        return EnseignantMapper.enseignantToEnseignantDto(enseignantService.getEnseignantById(id));
    }

    @Operation(summary = "Récupérer un enseignant par ID utilisateur", description = "Cette méthode permet de récupérer un enseignant par l'ID de son utilisateur.")
    @GetMapping("/userId/{id}")
    public EnseignantDto getEnseignantByUserId(@PathVariable Long id) {
        return EnseignantMapper.enseignantToEnseignantDto(
                this.enseignantService.getEnseignantByUser(id)
        );
    }

    @Operation(summary = "Mettre à jour un enseignant", description = "Cette méthode permet de mettre à jour un enseignant existant.")
    @PutMapping()
    public  EnseignantDto updateEnseignant(@RequestBody EnseignantDto enseignant) {

        if(enseignant.getUser() != null && enseignant.isHasAccount())
        {
            if(enseignant.getId() == null){
                return EnseignantMapper.enseignantToEnseignantDto(
                        this.enseignantService.createEnseignant(
                                enseignant.getUser(),
                                enseignant.getMaxHeuresService(),
                                0,
                                enseignant.getCategorieEnseignant(),
                                enseignant.getNbHeureCategorie(),
                                1L
                        )
                );
            }
            return EnseignantMapper.enseignantToEnseignantDto(
                    this.enseignantService.updateEnseignant(
                            enseignant.getId(),
                            enseignant.getMaxHeuresService(),
                            enseignant.getCategorieEnseignant(),
                            enseignant.getNbHeureCategorie(),
                            enseignant.getUser(),
                            enseignant.isHasAccount()
                    )
            );
        }
       return EnseignantMapper.enseignantToEnseignantDto(
                this.enseignantService.updateEnseignant(
                        enseignant.getId(),
                        enseignant.getMaxHeuresService(),
                        enseignant.getCategorieEnseignant(),
                        enseignant.getNbHeureCategorie(),
                        enseignant.getName(),
                        enseignant.getFirstname(),
                        enseignant.isHasAccount()
                ));
    }

    @Operation(summary = "Mettre à jour le commentaire d'une affectation", description = "Cette méthode permet de mettre à jour le commentaire d'une affectation existante.")
    @PutMapping("/{id}/affectations/{idAffectation}/commentaire")
    public CommentaireDto updateCommentaireAffectation(@PathVariable Long idAffectation,
                                                       @RequestBody CommentaireDto commentaireDto,
                                                       @CurrentSecurityContext(expression = "authentication?.name") String username) {
        enseignantService.updateCommentaireAffectation(idAffectation, username, commentaireDto.getCommentaire());
        return CommentaireDto.builder().commentaire(commentaireDto.getCommentaire()).build();
    }

    @Operation(summary = "Trouver des enseignants par nom et prénom", description = "Cette méthode permet de trouver des enseignants ayant le même nom et prénom qu'un utilisateur.")
    @GetMapping("/findenseignant")
    public ResponseEntity<List<EnseignantDto>> getEnseignantsWithSameUserNameAndFirstName(
            @RequestParam String name,
            @RequestParam String firstname) {
        return ResponseEntity.ok(
                this.enseignantService.getEnseignantsWithSameUserNameAndFirstName(
                        name,
                        firstname
                ).stream().map(EnseignantMapper::enseignantToEnseignantDto).collect(Collectors.toList())
        );
    }

    @Operation(summary = "Récupérer des enseignants par nom", description = "Cette méthode permet de récupérer des enseignants par leur nom.")
    @GetMapping("/by-name/{name}")
    public List <EnseignantDto> getEnseignantByName(@PathVariable String name) {
        return this.enseignantService.getEnseignantByName(name).stream().map(EnseignantMapper::enseignantToEnseignantDto).collect(Collectors.toList());
    }

    @Operation(summary = "Récupérer des enseignants par prénom", description = "Cette méthode permet de récupérer des enseignants par leur prénom.")
    @GetMapping("/by-firstname/{firstname}")
    public List<EnseignantDto> getEnseignantByFirstname(@PathVariable String firstname) {
        return this.enseignantService.getEnseignantByFirstname(firstname).stream().map(EnseignantMapper::enseignantToEnseignantDto).collect(Collectors.toList());
    }
}
