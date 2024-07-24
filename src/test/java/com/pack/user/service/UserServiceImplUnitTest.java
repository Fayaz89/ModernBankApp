package com.pack.user.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pack.exception.UserIdAlreadyExistsException;
import com.pack.exception.UserNotFoundException;
import com.pack.model.User;
import com.pack.repository.UserRepository;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceImplUnitTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#createUser(User)}
     */
    @Test
    void testExistingUserOnCreation() throws UserIdAlreadyExistsException {
        // tests if an exisiting user is identified
        // Arrange
        when(userRepository.existsById(Mockito.<Integer>any())).thenReturn(true);

        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("jkdfgvjdgvj");
        user.setUserId(1);

        // Act and Assert
        assertThrows(UserIdAlreadyExistsException.class, () -> userServiceImpl.createUser(user));
        verify(userRepository).existsById(eq(1));
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(User)}
     */
    @Test
    void testCreateUser() throws UserIdAlreadyExistsException {
        // checks if a user is created
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("jkdfgvjdgvj");
        user.setUserId(1);
        when(userRepository.existsById(Mockito.<Integer>any())).thenReturn(false);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setContactNumber("42");
        user2.setEmail("jane.doe@example.org");
        user2.setName("Name");
        user2.setPassword("jkdfgvjdgvj");
        user2.setUserId(1);

        // Act
        User actualCreateUserResult = userServiceImpl.createUser(user2);

        // Assert
        verify(userRepository).existsById(eq(1));
        verify(userRepository).save(isA(User.class));
        assertSame(user, actualCreateUserResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        // Arrange
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> actualAllUsers = userServiceImpl.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isEmpty());
        assertSame(userList, actualAllUsers);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(int)}
     */
    @Test
    void testGetUserById() throws UserNotFoundException {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("jkdfgvjdgvj");
        user.setUserId(1);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        User actualUserById = userServiceImpl.getUserById(1);

        // Assert
        verify(userRepository).findById(eq(1));
        assertSame(user, actualUserById);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(int)}
     */
    @Test
    void testGetUserByIdNotFound() throws UserNotFoundException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(1));
        verify(userRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(int)}
     */
    @Test
    void testDeleteUser() throws UserNotFoundException {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("jkdfgvjdgvj");
        user.setUserId(1);
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        User actualDeleteUserResult = userServiceImpl.deleteUser(1);

        // Assert
        verify(userRepository).delete(isA(User.class));
        verify(userRepository).findById(eq(1));
        assertSame(user, actualDeleteUserResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(int)}
     */
    @Test
    void testUserNotFoundOnDeletion() throws UserNotFoundException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUser(1));
        verify(userRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(User)}
     */
    @Test
    void testUpdateUser() throws UserNotFoundException {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("jkdfgvjdgvj");
        user.setUserId(1);
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setContactNumber("42");
        user2.setEmail("jane.doe@example.org");
        user2.setName("Name");
        user2.setPassword("jkdfgvjdgvj");
        user2.setUserId(1);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        User updatedUser = new User();
        updatedUser.setContactNumber("42");
        updatedUser.setEmail("jane.doe@example.org");
        updatedUser.setName("Name");
        updatedUser.setPassword("jkdfgvjdgvj");
        updatedUser.setUserId(1);

        // Act
        User actualUpdateUserResult = userServiceImpl.updateUser(updatedUser);

        // Assert
        verify(userRepository).findById(eq(1));
        verify(userRepository).save(isA(User.class));
        assertSame(user2, actualUpdateUserResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(User)}
     */
    @Test
    void testUserNotFoundOnUpdate() throws UserNotFoundException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        User updatedUser = new User();
        updatedUser.setContactNumber("42");
        updatedUser.setEmail("jane.doe@example.org");
        updatedUser.setName("Name");
        updatedUser.setPassword("jkdfgvjdgvj");
        updatedUser.setUserId(1);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(updatedUser));
        verify(userRepository).findById(eq(1));
    }

    /**
     * Method under test: {@link UserServiceImpl#loginUser(String, String)}
     */
    @Test
    void testLoginUser() throws UserNotFoundException {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("jkdfgvjdgvj");
        user.setUserId(1);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmailAndPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

        // Act
        User actualLoginUserResult = userServiceImpl.loginUser("jane.doe@example.org", "jkdfgvjdgvj");

        // Assert
        verify(userRepository).findByEmailAndPassword(eq("jane.doe@example.org"), eq("jkdfgvjdgvj"));
        assertSame(user, actualLoginUserResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#loginUser(String, String)}
     */
    @Test
    void testUserNotFoundOnLogin() throws UserNotFoundException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByEmailAndPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.loginUser("jane.doe@example.org", "jkdfgvjdgvj"));
        verify(userRepository).findByEmailAndPassword(eq("jane.doe@example.org"), eq("jkdfgvjdgvj"));
    }
}