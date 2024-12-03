package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.AuthResponse;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.security.JWTUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceDefault implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;  // Inject JWT utility

    public UserServiceDefault() {
    }

    public UserServiceDefault(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void postConstruct() {
        /*
            Comptes de tests
         */


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = List.of(
                new User("cdd", passwordEncoder.encode("cdd"), "cdd@cdd.fr",
                        Map.of(2023, Role.CHEF_DE_DEPARTEMENT, 2024, Role.CHEF_DE_DEPARTEMENT)),
                new User("rdf", passwordEncoder.encode("rdf"), "rdf@rdf.fr",
                        Map.of(2023, Role.RESPONSABLE_DE_FORMATION, 2024, Role.RESPONSABLE_DE_FORMATION)),
                new User("ens", passwordEncoder.encode("ens"), "ens@ens.fr",
                        Map.of(2023, Role.ENSEIGNANT, 2024, Role.ENSEIGNANT)),
                new User("sec", passwordEncoder.encode("sec"), "sec@sec.fr",
                        Map.of(2023, Role.SECRETARIAT_PEDAGOGIQUE, 2024, Role.SECRETARIAT_PEDAGOGIQUE))
        );
        for(User u : users)
            userRepository.findUserByEmail(u.getEmail()).orElseGet(() -> userRepository.save(u));

    }

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

    public Role getCurrentRole(User user) {
        int currentYear = LocalDate.now().getYear(); // Get the current year
        Map<Integer, Role> roles = user.getRoles();

        // If there are no roles, return null (or some default role)
        if (roles.isEmpty()) {
            return null;
        }

        // Find the role for the current year, or the most recent year available
        Role currentRole = roles.get(currentYear);
        if (currentRole != null) {
            return currentRole; // Return role for the current year if exists
        }

        // If there's no role for the current year, return the role of the most recent year
        return roles.entrySet().stream()
                .max(Map.Entry.comparingByKey()) // Get the most recent year (max year)
                .map(Map.Entry::getValue)        // Get the role for that year
                .orElse(null);                   // Return null if no roles exist
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
}
