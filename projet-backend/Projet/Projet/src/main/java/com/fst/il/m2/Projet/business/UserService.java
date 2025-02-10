package com.fst.il.m2.Projet.business;


import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;

import java.util.List;

public interface UserService {
    User authenticate(String email, String password);
    void modifyPassword(Long id, String password);
    User getUserByEmail(String email);
    List<User> getAllUsersNotTeachers();
    List<Role> getCurrentRoles(User user);

    User getUserByUsername(String username);
}
