package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AnneeService;
import com.fst.il.m2.Projet.business.UserService;
import com.fst.il.m2.Projet.dto.AuthResponse;
import com.fst.il.m2.Projet.dto.UserAuthentification;
import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.mapper.UserMapper;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.security.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AnneeService anneeService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, AnneeService anneeService) {
        this.userService = userService;
        this.anneeService = anneeService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(HttpServletResponse response, @RequestBody UserAuthentification user) {
        System.out.println(user);
        User authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        System.out.println(authenticatedUser);
        if (authenticatedUser == null) throw new UnauthorizedException("Authentication failed");

        // Get the current role (the role for the current year)
        List<Role> currentRoles = userService.getCurrentRoles(authenticatedUser);

        // Generate JWT token here with the most recent role
        String token = jwtUtil.generateToken(authenticatedUser.getUsername(),
                currentRoles != null ? currentRoles.stream().map(Enum::name).toList() : null); // Only the current role

        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        //cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity<>(
                AuthResponse.builder()
                        .message("Authentication succeeded")
                        .currentYearId(anneeService.getCurrentYearId())
                        .user(UserMapper.userToUserDto(authenticatedUser))
                        .build()
                , HttpStatus.OK);
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

    /**
     * Logout the user by deleting the token cookie
     * @param response : the response object
     */
    @PostMapping("/user/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @GetMapping("/user/me")
    public UserRequest.UserDto getCurrentUser(@CurrentSecurityContext(expression = "authentication?.name") String username) {
        return UserMapper.userToUserDto(userService.getUserByUsername(username));
    }
}
