package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;

public interface UserService {
    public int authenticate(String email, String password);
    public void modifyPassword(Long id, String password);
    public Role getUserRole(Long id);
}
