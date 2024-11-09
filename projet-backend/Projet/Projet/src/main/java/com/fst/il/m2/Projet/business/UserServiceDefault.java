package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.AuthResponse;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import com.fst.il.m2.Projet.security.JWTUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
    public void postConstruct(){
        /*
            Comptes de tests
         */


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userRepository.save(new User("cdd", passwordEncoder.encode("cdd"), "cdd@cdd.fr", Arrays.asList(Role.CHEF_DE_DEPARTEMENT)));
        userRepository.save(new User("rdd", passwordEncoder.encode("rdd"), "rdd@rdd.fr", Arrays.asList(Role.RESPONSABLE_DE_FORMATION)));
        userRepository.save(new User("ens", passwordEncoder.encode("ens"), "ens@ens.fr", Arrays.asList(Role.ENSEIGNANT)));
        userRepository.save(new User("sec", passwordEncoder.encode("sec"), "sec@sec.fr", Arrays.asList(Role.SECRETARIAT_PEDAGOGIQUE)));

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
}
