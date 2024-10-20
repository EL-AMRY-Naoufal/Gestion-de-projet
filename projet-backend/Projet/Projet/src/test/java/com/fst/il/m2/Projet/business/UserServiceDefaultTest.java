package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceDefaultTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceDefault userService;

    private User mockUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User("testUser", "password123", "test@example.com", Role.ENSEIGNANT);

        when(userRepository.findUserByEmail(mockUser.getEmail())).thenReturn(Optional.ofNullable(mockUser));
    }

    @Test
    public void shouldAuthenticateWithSuccess() {

        UserRequest userRequest = new UserRequest.Builder()
                .setUser(mockUser)
                .setResponsableId(1L)
                .build();

        int isAuthenticated = userService.authenticate(userRequest.getUser().getEmail(), userRequest.getUser().getPassword());

        assertEquals(1, isAuthenticated, "The user should be authenticated successfully");
    }

    @Test
    public void shouldFailAuthenticationWithInvalidPassword() {
        UserRequest userRequest = new UserRequest.Builder()
                .setUser(mockUser)
                .setResponsableId(1L)
                .build();

        int isAuthenticated = userService.authenticate(userRequest.getUser().getEmail(), "wrongPassword");

        assertEquals(0, isAuthenticated, "Authentication should fail with an incorrect password");
    }

    @Test
    public void shouldFailAuthenticationWithNonExistingUser() {
        when(userRepository.findUserByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        int isAuthenticated = userService.authenticate("nonexistent@example.com", "password123");

        assertEquals(0, isAuthenticated, "Authentication should fail for a non-existing user");
    }
}
