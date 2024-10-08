package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        System.out.println(user);
        int result = userService.authenticate(user.getEmail(), user.getPassword());
        if (result == 1) {
            return new ResponseEntity<>("Authentication successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
    }
}
