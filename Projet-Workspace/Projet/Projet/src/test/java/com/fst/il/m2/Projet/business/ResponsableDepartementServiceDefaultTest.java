package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResponsableDepartementServiceDefaultTest {

    @Mock
    private UserRepository userRepository;  // Mocked UserRepository

    @InjectMocks
    private ResponsableDepartementServiceDefault responsableDepartementService;  // Service to test

    private User mockResponsable;
    private User mockUser;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create mock Responsable (CHEF_DE_DEPARTEMENT)
        mockResponsable = new User("responsable", "password123", "responsable@example.com", Role.CHEF_DE_DEPARTEMENT);

        // Create mock User
        mockUser = new User("newUser", "password123", "newUser@example.com", Role.ENSEIGNANT);
    }

    @Test
    void testCreateUser_Success() {
        // Mock repository behavior for finding the responsable
        when(userRepository.findById(mockResponsable.getId())).thenReturn(Optional.of(mockResponsable));
        // Mock repository behavior for saving the user
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Act: Create user using the service
        User createdUser = responsableDepartementService.createUser(mockUser, mockResponsable.getId());

        // Assert: The user should be created successfully
        assertNotNull(createdUser);
        assertEquals(mockUser.getUsername(), createdUser.getUsername());
        assertEquals(mockUser.getEmail(), createdUser.getEmail());
        assertEquals(mockUser.getRole(), createdUser.getRole());
    }

    @Test
    void testCreateUser_Fail_NotResponsable() {
        // Create a mock user with a non-Responsable role
        User mockNonResponsable = new User("nonResponsable", "password123", "nonResponsable@example.com", Role.ENSEIGNANT);

        // Mock repository behavior for finding the non-Responsable
        when(userRepository.findById(mockNonResponsable.getId())).thenReturn(Optional.of(mockNonResponsable));

        // Act and Assert: Attempting to create a user should throw an exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            responsableDepartementService.createUser(mockUser, mockNonResponsable.getId());
        });

        assertEquals("Only Responsable de Département can create users", thrown.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        // Mock repository behavior for finding a user
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        // Act: Retrieve user by ID
        User foundUser = responsableDepartementService.getUserById(mockUser.getId());

        // Assert: The user should be returned successfully
        assertNotNull(foundUser);
        assertEquals(mockUser.getId(), foundUser.getId());
        assertEquals(mockUser.getUsername(), foundUser.getUsername());
    }

    @Test
    void testGetUserById_Fail_UserNotFound() {
        // Mock repository behavior for finding a user
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.empty());

        // Act and Assert: Attempting to get a non-existing user should throw an exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            responsableDepartementService.getUserById(mockUser.getId());
        });

        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        // Mock repository behavior for finding the responsable
        when(userRepository.findById(mockResponsable.getId())).thenReturn(Optional.of(mockResponsable));
        // Mock repository behavior for finding the user to update
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        // Mock repository behavior for saving the updated user
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        System.out.println(mockResponsable.getRole());
        // Act: Update user using the service
        User updatedUser = responsableDepartementService.updateUser(mockUser.getId(), mockUser, mockResponsable.getId());

        // Assert: The user should be updated successfully
        assertNotNull(updatedUser);
        assertEquals(mockUser.getUsername(), updatedUser.getUsername());
        assertEquals(mockUser.getEmail(), updatedUser.getEmail());
    }

    @Test
    void testUpdateUser_Fail_NotResponsable() {
        // Create a mock user with a non-Responsable role
        User mockNonResponsable = new User("nonResponsable", "password123", "nonResponsable@example.com", Role.ENSEIGNANT);

        // Mock repository behavior for finding the non-Responsable
        when(userRepository.findById(mockNonResponsable.getId())).thenReturn(Optional.of(mockNonResponsable));

        // Act and Assert: Attempting to update a user should throw an exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            responsableDepartementService.updateUser(mockUser.getId(), mockUser, mockNonResponsable.getId());
        });

        assertEquals("Only Responsable de Département can update users", thrown.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        // Mock repository behavior for finding the responsable
        when(userRepository.findById(mockResponsable.getId())).thenReturn(Optional.of(mockResponsable));
        // Mock repository behavior for deleting the user
        doNothing().when(userRepository).deleteById(mockUser.getId());

        // Act: Delete user using the service
        responsableDepartementService.deleteUser(mockUser.getId(), mockResponsable.getId());

        // Assert: No exception should be thrown, and delete was invoked
        verify(userRepository, times(1)).deleteById(mockUser.getId());
    }

    @Test
    void testDeleteUser_Fail_NotResponsable() {
        // Create a mock user with a non-Responsable role
        User mockNonResponsable = new User("nonResponsable", "password123", "nonResponsable@example.com", Role.ENSEIGNANT);

        // Mock repository behavior for finding the non-Responsable
        when(userRepository.findById(mockNonResponsable.getId())).thenReturn(Optional.of(mockNonResponsable));

        // Act and Assert: Attempting to delete a user should throw an exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            responsableDepartementService.deleteUser(mockUser.getId(), mockNonResponsable.getId());
        });

        assertEquals("Only Responsable de Département can delete users", thrown.getMessage());
    }
}
