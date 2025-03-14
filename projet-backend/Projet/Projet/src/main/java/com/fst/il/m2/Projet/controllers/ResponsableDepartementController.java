package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.business.UserServiceDefault;
import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.mapper.UserMapper;
import com.fst.il.m2.Projet.models.ResponsableDepartement;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.models.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/responsableDepartement")
@Tag(name = "Responsable Departement Controller", description = "Gestion des responsables de département")

public class ResponsableDepartementController {

    @Autowired
    private ResponsableDepartementService responsableDepartementService;

    @Autowired
    private UserService userService;


    @Operation(summary = "Créer un utilisateur", description = "Cette méthode permet de créer un nouvel utilisateur.")
    @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès")
    @PostMapping
    public ResponseEntity<UserRequest.UserDto> createUser(@RequestBody UserRequest userRequest) {
        UserRequest.UserDto createdUser = UserMapper.userToUserDto(
                responsableDepartementService.createUser(
                    userRequest.getUser().toUser(),
                    userRequest.getResponsableId(),
                    userRequest.isAssociateEnseignantWithUser(), // Pass the new parameter,
                    userRequest.getYear()
                )
        );
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Récupérer un utilisateur par ID", description = "Cette méthode permet de récupérer un utilisateur par son ID.")
    @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès")
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = responsableDepartementService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Récupérer un responsable de département par ID", description = "Cette méthode permet de récupérer un responsable de département par son ID.")
    @ApiResponse(responseCode = "200", description = "Responsable de département récupéré avec succès")
    @GetMapping("{responsableId}")
    public ResponseEntity<ResponsableDepartement> getResponsableDepartement(@PathVariable Long responsableId) {
        ResponsableDepartement responsableDepartement = responsableDepartementService.getResponsableDepartementById(responsableId);
        return ResponseEntity.ok(responsableDepartement);
    }
    @GetMapping
    public ResponseEntity<List<UserRequest.UserDto>> getAllUsers(@RequestParam(required = false) String q) {
        if(q != null){
            List<UserRequest.UserDto> users = userService.searchUsers(q).stream()
                    .map(UserMapper::userToUserDto)
                    .toList();
            return ResponseEntity.ok(users);
        }
        List<UserRequest.UserDto> users = responsableDepartementService.getAllUsers().stream()
                .map(UserMapper::userToUserDto)
                .toList();
        return ResponseEntity.ok(users);
    }
    /*@GetMapping("/{username}")
    public ResponseEntity<List<User>> getAllUserByUsername(@PathVariable String username) {
        List<User> users = responsableDepartementService.getUsersByUsername(username);
        return ResponseEntity.ok(users);
    }*/

    @Operation(summary = "Récupérer les utilisateurs par rôle", description = "Cette méthode permet de récupérer les utilisateurs par rôle.")
    @ApiResponse(responseCode = "200", description = "Utilisateurs récupérés avec succès")
    @GetMapping("/users/by-just-role")
    public List<User> getUsersByRole(@RequestParam Role role) {
        List<UserRole> userRoles = responsableDepartementService.getUsersByRole(role);
        return userRoles.stream()
                .map(UserRole::getUser)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Récupérer les utilisateurs par rôle et année", description = "Cette méthode permet de récupérer les utilisateurs par rôle et année.")
    @ApiResponse(responseCode = "200", description = "Utilisateurs récupérés avec succès")
    @GetMapping("/users/by-role-and-year")
    public List<User> getUsersByRoleAndYear(@RequestParam Role role, @RequestParam Long year, @RequestParam(required = false) String q) {
        String query = q == null ? "" : q;
        return responsableDepartementService.getUsersByRoleAndYear(query, role, year);
    }
    @Operation(summary = "Récupérer les rôles d'un utilisateur par ID et année", description = "Cette méthode permet de récupérer les rôles d'un utilisateur par son ID et année.")
    @ApiResponse(responseCode = "200", description = "Rôles récupérés avec succès")
    @GetMapping("/user/{userId}/year/{year}")
    public  ResponseEntity<List<UserRole>> getUserRoles(@PathVariable Long userId, @PathVariable Long year) {
        List<UserRole> userRolesByYear =   responsableDepartementService.getRolesByUserIdAndYear(userId, year);
        return ResponseEntity.ok(userRolesByYear);
    }

    @Operation(summary = "Mettre à jour un utilisateur", description = "Cette méthode permet de mettre à jour un utilisateur par son ID.")
    @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User updatedUser = responsableDepartementService.updateUser(id, userRequest.getUser().toUser(), userRequest.getResponsableId(), userRequest.getYear());
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Supprimer un utilisateur", description = "Cette méthode permet de supprimer un utilisateur par son ID.")
    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        responsableDepartementService.deleteUser(id, userRequest.getResponsableId());
        return ResponseEntity.noContent().build();
    }
}
