package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.exceptions.UnauthorizedException;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.models.UserRole;
import com.fst.il.m2.Projet.repositories.AnneeRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.repositories.UserRoleRepository;
import com.fst.il.m2.Projet.security.JWTUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceDefault implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnneeRepository anneeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JWTUtil jwtUtil;  // Inject JWT utility

    public UserServiceDefault() {
    }

    public UserServiceDefault(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*@PostConstruct
    public void postConstruct() {
        *//*
            Comptes de tests
         *//*

        // UserRoles
        Map<String, Role> userRoles = Map.of(
                "cdd", Role.CHEF_DE_DEPARTEMENT,
                "rdf", Role.RESPONSABLE_DE_FORMATION,
                "ens", Role.ENSEIGNANT,
                "sec", Role.SECRETARIAT_PEDAGOGIQUE
        );

        *//*for(UserRole ur : userRoles.values()){
            userRoleRepository.findAllByRoleAndYear(ur.getRole(), ur.getYear());
        }*//*

        List<Annee> annees = List.of(
                anneeRepository.findById(1L).orElseGet(() -> anneeRepository.save(Annee.builder().id(1L).debut(2024).build())),
                anneeRepository.findById(2L).orElseGet(() -> anneeRepository.save(Annee.builder().id(2L).debut(2025).build()))
        );

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = List.of(
                User.builder().username("cdd").password(passwordEncoder.encode("cdd")).email("cdd@cdd.fr").roles(new ArrayList<>()).name("cdd").firstname("cdd").build(),
                User.builder().username("rdf").password(passwordEncoder.encode("rdf")).email("rdf@rdf.fr").roles(new ArrayList<>()).name("rdf").firstname("rdf").build(),
                User.builder().username("ens").password(passwordEncoder.encode("ens")).email("ens@ens.fr").roles(new ArrayList<>()).name("ens").firstname("ens").build(),
                User.builder().username("sec").password(passwordEncoder.encode("sec")).email("sec@sec.fr").roles(new ArrayList<>()).name("sec").firstname("sec").build()
        );
        for(User u : users){
            u.addRole(Annee.builder().id(1L).build(), userRoles.get(u.getUsername()));
            userRepository.findUserByEmail(u.getEmail()).orElseGet(() -> userRepository.save(u));
        }
    }*/

    @Override
    public User authenticate(String email, String password) {  // Return type is User
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Use BCrypt to check if the plain password matches the hashed one
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;  // Return the user if authenticated
            }
        }
        return null;
    }

    public List<Role> getCurrentRoles(User user) {
        Long currentYearId = anneeRepository.getCurrentYear().orElseThrow(NotFoundException::new).getId();
        List<Role> roles = user.getRoles().stream()
                .filter(userRole -> userRole.getYear().getId().equals(currentYearId))
                .map(UserRole::getRole)
                .toList();

        // If there are no roles, return null (or some default role)
        if (roles.isEmpty()) {
            return null;
        }

        return roles;
    }

    @Override
    public void modifyPassword(Long id,String password) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + "not found"));
        user.setPassword(password);
        this.userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.orElse(null);

    }
        
    public List<User> getAllUsersNotTeachers() {
        return this.userRepository.findUsersByRolesNotLike(Role.ENSEIGNANT);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findOneUserByUsername(username).orElseThrow(NotFoundException::new);
    }
}
