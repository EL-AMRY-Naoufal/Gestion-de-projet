package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceDefault implements UserService {

    @Autowired
    private UserRepository userRepository;

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
        List<User> users = List.of(
                new User("cdd", "cdd", "cdd@cdd.fr", Role.CHEF_DE_DEPARTEMENT),
                new User("rdf", "rdf", "rdf@rdf.fr", Role.RESPONSABLE_DE_FORMATION),
                new User("ens", "ens", "ens@ens.fr", Role.ENSEIGNANT),
                new User("sec", "sec", "sec@sec.fr", Role.SECRETARIAT_PEDAGOGIQUE)
        );

        for(User u : users)
            userRepository.findUserByEmail(u.getEmail()).orElseGet(() -> userRepository.save(u));

    }

    @Override
    public User authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
