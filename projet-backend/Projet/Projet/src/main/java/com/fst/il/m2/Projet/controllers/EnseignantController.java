package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.EnseignantService;
import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.CommentaireDto;
import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.mapper.EnseignantMapper;
import com.fst.il.m2.Projet.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@Validated
public class EnseignantController {
    @Autowired
    private  UserService userService;

    @Autowired
    private  EnseignantService enseignantService;


    
    @GetMapping("/enseignants-non-enregistres")
    public ResponseEntity<List<User>> getEnseignantsNotInEnseignantTable() {
        List<User> users = enseignantService.getUsersWithRoleEnseignantNotInEnseignant();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/finduser")
    public ResponseEntity<List<User>> getUsersWithSameEnseignantNameAndFirstName(
            @RequestParam String name,
            @RequestParam String firstname) {
        List<User> users = userService.findUsersByEnseignantNameAndFirstName(name, firstname);

        return ResponseEntity.ok(users);
    }
    @GetMapping()
    public List<EnseignantDto> getEnseignants(){
        return enseignantService.getEnseignants().stream().map(
                EnseignantMapper::enseignantToEnseignantDto
        ).collect(Collectors.toList());
    }

    @PostMapping()
    public EnseignantDto createEnseignant(@RequestBody EnseignantDto enseignant) {
        if(enseignant.isHasAccount()) {
            return EnseignantMapper.enseignantToEnseignantDto(this.enseignantService.createEnseignant(
                    enseignant.getUser(),
                    enseignant.getMaxHeuresService(),
                    enseignant.getHeuresAssignees(),
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
                    enseignant.getHeuresAssignees(),
                    enseignant.getCategorieEnseignant(),
                    enseignant.getNbHeureCategorie()
            ));
        }
    }

    @GetMapping("{id}")
    public EnseignantDto getEnseignantById(@PathVariable Long id) {
        return EnseignantMapper.enseignantToEnseignantDto(enseignantService.getEnseignantById(id));
    }

    @GetMapping("/userId/{id}")
    public EnseignantDto getEnseignantByUserId(@PathVariable Long id) {
        return EnseignantMapper.enseignantToEnseignantDto(
                this.enseignantService.getEnseignantByUser(id)
        );
    }

    @PutMapping()
    public  EnseignantDto updateEnseignant(@RequestBody EnseignantDto enseignant) {

        if(enseignant.getUser() != null && enseignant.isHasAccount())
        {
            if(enseignant.getId() == null){
                return EnseignantMapper.enseignantToEnseignantDto(
                        this.enseignantService.createEnseignant(
                                enseignant.getUser(),
                                enseignant.getMaxHeuresService(),
                                enseignant.getHeuresAssignees(),
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
                )
        );
    }

    @PutMapping("/{id}/affectations/{idAffectation}/commentaire")
    public CommentaireDto updateCommentaireAffectation(@PathVariable Long idAffectation, @RequestBody CommentaireDto commentaireDto, @CurrentSecurityContext(expression = "authentication?.name") String username){
        enseignantService.updateCommentaireAffectation(idAffectation, username, commentaireDto.getCommentaire());

        return CommentaireDto.builder().commentaire(commentaireDto.getCommentaire()).build();
    }

    @GetMapping("/findenseignant")
    public ResponseEntity<List<EnseignantDto>> getEnseignantsWithSameUserNameAndFirstName(
            @RequestParam String name,
            @RequestParam String firstname){
        return ResponseEntity.ok(
                this.enseignantService.getEnseignantsWithSameUserNameAndFirstName(
                        name,
                        firstname
                ).stream().map(EnseignantMapper::enseignantToEnseignantDto).collect(Collectors.toList())
        );
    }

    @GetMapping("/by-name/{name}")
    public List <EnseignantDto> getEnseignantByName(@PathVariable String name) {
        return this.enseignantService.getEnseignantByName(name).stream().map(EnseignantMapper::enseignantToEnseignantDto).collect(Collectors.toList());
    }

    @GetMapping("/by-firstname/{firstname}")
    public List<EnseignantDto> getEnseignantByFirstname(@PathVariable String firstname) {
        return this.enseignantService.getEnseignantByFirstname(firstname).stream().map(EnseignantMapper::enseignantToEnseignantDto).collect(Collectors.toList());
    }
}
