package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.models.User;
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
    public ResponseEntity<HashMap<String, String>> authenticate(@RequestBody User user) {
        System.out.println(user);
        HashMap<String, String> res = new HashMap<>();
        int result = userService.authenticate(user.getEmail(), user.getPassword());

        if (result != 1) throw new UnauthorizedException("Authentification failed");
        res.put("message", "Authentication successful");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
