package com.fst.il.m2.Projet.business;


import com.fst.il.m2.Projet.models.User;

import java.util.List;

public interface UserService {
    public User authenticate(String email, String password);
    public void modifyPassword(Long id, String password);
    public User getUserByEmail(String email);
    public List<User> getAllUsersNotTeachers();
}
