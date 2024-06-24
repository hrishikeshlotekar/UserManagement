package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.rishabhsoft.dto.UserDetailsDto;
import com.rishabhsoft.model.User;
import com.rishabhsoft.repository.UserRepository;
import com.rishabhsoft.service.UserService;
import com.rishabhsoft.service.UserServiceImpl;
import org.apache.struts2.junit.StrutsSpringTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementActionTest extends StrutsSpringTestCase {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserManagementAction userManagementAction;

    private UserService userService;

    @Before
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        // Initialize signUpAction and set dependencies
        userManagementAction = new UserManagementAction();
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        userManagementAction.setUserService(userService);
    }

    @Test
    public void setUserService() {
        userManagementAction.setUserService(userService);
        assertNotNull(userManagementAction.getUserService());
    }

    @Test
    public void getModel() {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        userManagementAction.setUser(user);

        // Execute the action
        UserDetailsDto result = userManagementAction.getModel();
    }

    @Test
    public void display() {

        // Execute the action
        String result = userManagementAction.display();
        assertEquals("Expected SUCCESS result", ActionSupport.SUCCESS, result);

    }

    @Test
    public void listUsers() {
        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");
        PageImpl<User> users1 = new PageImpl<>(List.of(user1), PageRequest.of(0, 10), 10);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(users1);

        String result = userManagementAction.listUsers();

        verify(userRepository, times(1)).findAll(any(Pageable.class));
        assertEquals("Expected SUCCESS result", ActionSupport.SUCCESS, result);
    }

    @Test
    public void listUsersError() {
        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");
        PageImpl<User> users1 = new PageImpl<>(List.of(user1), PageRequest.of(0, 10), 10);

        // Set up the repository to throw an exception when findAll is called
        doThrow(new RuntimeException("List failed")).when(userRepository).findAll(any(Pageable.class));

        // Execute the action
        String result = userManagementAction.listUsers();

        // Verify the repository interaction and result
        verify(userRepository, times(1)).findAll(any(Pageable.class));
        assertEquals("Expected ERROR result", ActionSupport.ERROR, result);
    }

    @Test
    public void addUser() {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        userManagementAction.setUser(user);

        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");

        // Mock userService behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        // Execute the action
        String result = userManagementAction.addUser();

        // Verify interactions and assert result
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("Expected SUCCESS result", ActionSupport.SUCCESS, result);


    }

    @Test
    public void addUserEmailExists() {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        userManagementAction.setUser(user);

        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");

        // Mock userService behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user1);
        // Execute the action
        String result = userManagementAction.addUser();

        // Verify interactions and assert result
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(0)).save(any(User.class));
        assertEquals("Expected INPUT result", ActionSupport.INPUT, result);

    }

    @Test
    public void addUserError() {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        userManagementAction.setUser(user);

        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");

        // Mock userService behavior to throw an exception
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        when(passwordEncoder.encode(user.getPassword())).thenReturn("EncodedPassword");

        doThrow(new RuntimeException("Save failed")).when(userRepository).save(any(User.class));

        // Execute the action
        String result = userManagementAction.addUser();

        // Verify interactions and assert result
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("Expected INPUT result", ActionSupport.INPUT, result);
    }


    @Test
    public void deleteUser() throws Exception {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        userManagementAction.setUser(user);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");


        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        // Execute the action
        String result = userManagementAction.deleteUser();

        // Verify the results
        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).deleteById(user1.getId());
        assertEquals("Expected SUCCESS result", ActionSupport.SUCCESS, result);
    }

    @Test
    public void deleteUserError() throws Exception {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        userManagementAction.setUser(user);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");


        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        doThrow(new RuntimeException("Delete failed")).when(userRepository).deleteById(user1.getId());

        // Execute the action
        String result = userManagementAction.deleteUser();

        // Verify the results
        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).deleteById(user1.getId());
        assertEquals("Expected ERROR result", ActionSupport.ERROR, result);


    }
}