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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceDefaultTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceDefault userService;

    private User mockUser;
    private User user;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockUser = new User("testUser", passwordEncoder.encode("password123"), "test@example.com", Role.ENSEIGNANT);

        when(userRepository.findUserByEmail(mockUser.getEmail())).thenReturn(Optional.ofNullable(mockUser));

        user = new User(1L, "testUser", "password123", "test@example.com", Role.ENSEIGNANT);
    }

    @Test
    public void shouldAuthenticateWithSuccess() {

        UserRequest userRequest = new UserRequest.Builder()
                .setUser(user)
                .setResponsableId(1L)
                .build();

        User authenticatedUser = userService.authenticate(userRequest.getUser().getEmail(),  userRequest.getUser().getPassword());

        assertNotNull(authenticatedUser, "The user should be authenticated successfully");
        assertEquals(mockUser.getEmail(), authenticatedUser.getEmail(), "The authenticated user email should match the input email");
    }


    @Test
    public void shouldFailAuthenticationWithInvalidPassword() {
        UserRequest userRequest = new UserRequest.Builder()
                .setUser(user)
                .setResponsableId(1L)
                .build();

        User authenticatedUser = userService.authenticate(userRequest.getUser().getEmail(), "wrongPassword");

        assertNull(authenticatedUser, "Authentication should fail with an incorrect password");
    }

    @Test
    public void shouldFailAuthenticationWithNonExistingUser() {
        when(userRepository.findUserByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        User authenticatedUser = userService.authenticate("nonexistent@example.com", "password123");

        assertNull(authenticatedUser, "Authentication should fail for a non-existing user");
    }


}
