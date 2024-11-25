package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.User;

public interface UserService {
    public User authenticate(String email, String password);
}
