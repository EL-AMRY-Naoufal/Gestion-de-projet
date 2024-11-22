package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        userRepository.save(new User("cdd", "cdd", "cdd@cdd.fr", Role.CHEF_DE_DEPARTEMENT));
        userRepository.save(new User("rdd", "rdd", "rdd@rdd.fr", Role.RESPONSABLE_DE_FORMATION));
        userRepository.save(new User("ens", "ens", "ens@ens.fr", Role.ENSEIGNANT));
        userRepository.save(new User("sec", "sec", "sec@sec.fr", Role.SECRETARIAT_PEDAGOGIQUE));
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
}
