package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;

import com.fst.il.m2.Projet.models.User;

public interface UserService {
    public User authenticate(String email, String password);
    public void modifyPassword(Long id, String password);
    public Role getUserRole(Long id);
}
