package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.dto.UserRequest;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceDefaultTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceDefault userService;

    private User mockUser;

    @Before
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

        assertEquals("The user should be authenticated successfully", 1, isAuthenticated);
    }

    @Test
    public void shouldFailAuthenticationWithInvalidPassword() {
        UserRequest userRequest = new UserRequest.Builder()
                .setUser(mockUser)
                .setResponsableId(1L)
                .build();

        int isAuthenticated = userService.authenticate(userRequest.getUser().getEmail(), "wrongPassword");

        assertEquals("Authentication should fail with an incorrect password", 0, isAuthenticated);
    }

    @Test
    public void shouldFailAuthenticationWithNonExistingUser() {
        when(userRepository.findUserByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        int isAuthenticated = userService.authenticate("nonexistent@example.com", "password123");

        assertEquals("Authentication should fail for a non-existing user", 0, isAuthenticated);
    }
}
