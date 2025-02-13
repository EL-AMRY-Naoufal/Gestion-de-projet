package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.mapper.UserMapper;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/responsableDepartement")
public class ResponsableDepartementController {

    @Autowired
    private ResponsableDepartementService responsableDepartementService;

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

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = responsableDepartementService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<UserRequest.UserDto>> getAllUsers() {
        List<UserRequest.UserDto> users = responsableDepartementService.getAllUsers().stream()
                .map(UserMapper::userToUserDto)
                .toList();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<User>> getAllUserByUsername(@PathVariable String username) {
        List<User> users = responsableDepartementService.getUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/by-role")
    public List<User> getUsersByRole(@RequestParam Role role) {
        List<UserRole> userRoles = responsableDepartementService.getUsersByRole(role);
        return userRoles.stream()
                .map(UserRole::getUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/by-role-and-year")
    public List<User> getUsersByRoleAndYear(@RequestParam Role role, @RequestParam Long year) {
        List<UserRole> userRoles = responsableDepartementService.getUsersByRoleAndYear(role, year);
        System.out.println(userRoles.stream().map(UserRole::getId).toList());
        System.out.println(userRoles.stream().map(UserRole::getYear).toList());
        System.out.println(userRoles.stream().map(UserRole::getRole).toList());
        return userRoles.stream().map(UserRole::getUser).toList();
    }

    @GetMapping("/user/{userId}/year/{year}")
    public  ResponseEntity<List<UserRole>> getUserRoles(@PathVariable Long userId, @PathVariable Long year) {
        List<UserRole> userRolesByYear =   responsableDepartementService.getRolesByUserIdAndYear(userId, year);
        return ResponseEntity.ok(userRolesByYear);
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
    @PostMapping("/affectation/{idEnseignant}/{idGroupe}/{heure}")
    public ResponseEntity<String> affecterEnseignant(@PathVariable Long idEnseignant, @PathVariable Long idGroupe, @PathVariable int heure) {
        responsableDepartementService.affecterModuleToEnseignant(idEnseignant, idGroupe, heure);
        return ResponseEntity.ok("Enseignant affecté avec succès");
    }


}
