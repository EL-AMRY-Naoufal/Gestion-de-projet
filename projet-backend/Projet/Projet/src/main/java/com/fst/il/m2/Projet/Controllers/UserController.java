package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.AuthResponse;
import com.fst.il.m2.Projet.dto.UserAuthentification;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.security.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody UserAuthentification user) {
        System.out.println(user);
        User authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        System.out.println(authenticatedUser);
        if (authenticatedUser == null) throw new UnauthorizedException("Authentication failed");

        // Get the current role (the role for the current year)
        List<Role> currentRoles = userService.getCurrentRoles(authenticatedUser);

        // Generate JWT token here with the most recent role
        String token = jwtUtil.generateToken(authenticatedUser.getUsername(),
                currentRoles != null ? currentRoles.stream().map(Enum::name).toList() : null); // Only the current role

        AuthResponse response = new AuthResponse("Authentication succeeded", token, authenticatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public void modifyPassword(@PathVariable Long id, @Valid @RequestBody String password) {
        this.userService.modifyPassword(id, password);
    }

    @GetMapping("/user/{email}")
    public Long getUserIdByEmail(@PathVariable String email) {
        User user = this.userService.getUserByEmail(email);
        if(user == null) {
            return null;
        }
        else {
            return user.getId();
        }
    }

    @GetMapping()
    public List<User> getAllUsersNotTeachers() {
        return this.userService.getAllUsersNotTeachers();
    }
}
