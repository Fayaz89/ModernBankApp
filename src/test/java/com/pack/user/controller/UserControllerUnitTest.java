package com.pack.user.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pack.config.JWTTokenGeneratorImpl;
import com.pack.controller.UserController;
import com.pack.model.Login;
import com.pack.model.User;
import com.pack.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserControllerUnitTest {
    @MockBean
    private JWTTokenGeneratorImpl jWTTokenGeneratorImpl;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#createUser(User)}
     */
    @Test
    void testCreateUser() throws Exception {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId(1);
        when(userService.createUser(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setContactNumber("42");
        user2.setEmail("jane.doe@example.org");
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"userId\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"contactNumber\":\"42\"},\"message\":\"User created successfully\",\"status\":201}"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        // Arrange
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/viewAll");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseObj\":[],\"message\":\"Successfully retrieved data!\",\"status\":200}"));
    }

    /**
     * Method under test: {@link UserController#getUserById(int)}
     */
    @Test
    void testGetUserById() throws Exception {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId(1);
        when(userService.getUserById(anyInt())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{userId}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"userId\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"contactNumber\":\"42\"},\"message\":\"Successfully retrieved data!\",\"status\":200}"));
    }

    /**
     * Method under test: {@link UserController#updateUser(User)}
     */
    @Test
    void testUpdateUser() throws Exception {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId(1);
        when(userService.updateUser(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setContactNumber("42");
        user2.setEmail("jane.doe@example.org");
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/updateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"userId\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"contactNumber\":\"42\"},\"message\":\"User updated successfully\",\"status\":200}"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(int)}
     */
    @Test
    void testDeleteUser() throws Exception {
        // Arrange
        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId(1);
        when(userService.deleteUser(anyInt())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{userId}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObj\":{\"userId\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"contactNumber\":\"42\"},\"message\":\"User deleted successfully\",\"status\":200}"));
    }

    /**
     * Method under test: {@link UserController#loginUser(Login)}
     */
    @Test
    void testLoginUser() throws Exception {
        // Arrange
        when(jWTTokenGeneratorImpl.generateToken(Mockito.<User>any())).thenReturn(new HashMap<>());

        User user = new User();
        user.setContactNumber("42");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUserId(1);
        when(userService.loginUser(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);

        Login login = new Login();
        login.setEmail("jane.doe@example.org");
        login.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(login);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}