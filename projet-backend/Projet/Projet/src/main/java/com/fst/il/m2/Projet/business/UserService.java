package com.fst.il.m2.Projet.business;

public interface UserService {
    public int authenticate(String email, String password);
    public void modifyPassword(Long id, String password);
}
