package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.AuthResponse;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody User user) {
        System.out.println(user);
        User authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());

        if (authenticatedUser == null ) throw new UnauthorizedException("Authentification failed");
        AuthResponse response = new AuthResponse("Authentication successful", authenticatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public void modifyPassword(@PathVariable Long id, @Valid @RequestBody String password) {
        this.userService.modifyPassword(id, password);
        //TODO do it in a secured way?
    }

    @GetMapping("/user/{email}")
    public Long getUserIdByEmail(@PathVariable String email) {
        return this.userService.getUserByEmail(email).getId();
    }
}
