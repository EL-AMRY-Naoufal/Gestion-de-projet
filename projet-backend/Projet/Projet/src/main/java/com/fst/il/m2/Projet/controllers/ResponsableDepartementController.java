package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/responsableDepartement")
public class ResponsableDepartementController {

    @Autowired
    private ResponsableDepartementService responsableDepartementService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        User createdUser = responsableDepartementService.createUser(
                userRequest.getUser().toUser(),
                userRequest.getResponsableId(),
                userRequest.isAssociateEnseignantWithUser(), // Pass the new parameter,
                userRequest.getYear()
        );
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = responsableDepartementService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = responsableDepartementService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<User>> getAllUserByUsername(@PathVariable String username) {
        List<User> users = responsableDepartementService.getUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getAllUserByRole(@PathVariable String role) {
        Role roleEnum;
        try {
            roleEnum = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List <User> users = responsableDepartementService.getUsersByRole(roleEnum);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User updatedUser = responsableDepartementService.updateUser(id, userRequest.getUser().toUser(), userRequest.getResponsableId(), userRequest.getYear());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        responsableDepartementService.deleteUser(id, userRequest.getResponsableId());
        return ResponseEntity.noContent().build();
    }


    //route pour affecter un enseignant à un module en precisant lheure enseignée
    @PostMapping("/affectation/{idEnseignant}/{idModule}/{heure}")
    public ResponseEntity<String> affecterEnseignant(@PathVariable Long idEnseignant, @PathVariable Long idModule, @PathVariable int heure) {
        responsableDepartementService.affecterModuleToEnseignant(idEnseignant, idModule, heure);
        return ResponseEntity.ok("Enseignant affecté avec succès");
    }

}
