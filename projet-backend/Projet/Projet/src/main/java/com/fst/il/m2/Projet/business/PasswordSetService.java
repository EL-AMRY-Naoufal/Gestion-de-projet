package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.User;

public interface PasswordSetService {
    void createPasswordSetTokenForUser(User user, String token);
    void sendPasswordSetEmail(User user, String token);
}
