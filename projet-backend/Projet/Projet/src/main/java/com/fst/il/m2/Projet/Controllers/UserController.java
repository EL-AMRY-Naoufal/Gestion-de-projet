package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.AuthResponse;
import com.fst.il.m2.Projet.dto.UserAuthentification;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        if (authenticatedUser == null ) throw new UnauthorizedException("Authentication failed");

        // Generate JWT token here
        String token = jwtUtil.generateToken(authenticatedUser.getUsername(),
                authenticatedUser.getRoles().stream()
                        .map(Role::name)
                        .collect(Collectors.toList()));

        AuthResponse response = new AuthResponse("Authentication succeeded", token, authenticatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
