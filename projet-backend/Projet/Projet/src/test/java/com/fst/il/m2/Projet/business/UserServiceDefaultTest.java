/*
package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.models.UserRole;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceDefaultTest {
/*
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceDefault userService;

    private User mockUser;
    private User user;

    private static final Long currentYear = 1L;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockUser = User.builder()
                .username("testUser")
                .password(passwordEncoder.encode("password123"))
                .email("test@example.com")
                .roles(List.of(UserRole.builder().year(currentYear).role(Role.ENSEIGNANT).build()))
                .build();

        when(userRepository.findUserByEmail(mockUser.getEmail())).thenReturn(Optional.ofNullable(mockUser));

        //user = new User(1L, "testUser", "password123", "test@example.com", Map.of(1L, Role.ENSEIGNANT));
        user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password123")
                .email("test@example.com")
                .roles(List.of(UserRole.builder().year(currentYear).role(Role.ENSEIGNANT).build()))
                .build();
    }

 */

    // A corriger

/*
    @Test
    public void shouldAuthenticateWithSuccess() {

        UserRequest userRequest = UserRequest.builder()
                .user(userToUserDto(user))
                .responsableId(1L)
                .build();

        User authenticatedUser = userService.authenticate(userRequest.getUser().getEmail(),  userRequest.getUser().getPassword());

        assertNotNull(authenticatedUser, "The user should be authenticated successfully");
        assertEquals(mockUser.getEmail(), authenticatedUser.getEmail(), "The authenticated user email should match the input email");
    }


    @Test
    public void shouldFailAuthenticationWithInvalidPassword() {
        UserRequest userRequest = UserRequest.builder()
                .user(userToUserDto(user))
                .responsableId(1L)
                .build();

        User authenticatedUser = userService.authenticate(userRequest.getUser().getEmail(), "wrongPassword");

        assertNull(authenticatedUser, "Authentication should fail with an incorrect password");
    }


     */
    /*


    @Test
    public void shouldFailAuthenticationWithNonExistingUser() {
        when(userRepository.findUserByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        User authenticatedUser = userService.authenticate("nonexistent@example.com", "password123");

        assertNull(authenticatedUser, "Authentication should fail for a non-existing user");
    }


}*/
