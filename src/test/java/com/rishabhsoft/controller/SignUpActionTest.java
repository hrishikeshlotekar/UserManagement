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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SignUpActionTest extends StrutsSpringTestCase {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SignUpAction signUpAction;

    private UserService userService;

    @Before
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize signUpAction and set dependencies
        signUpAction = new SignUpAction();
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        signUpAction.setUserService(userService);
    }

    @Test
    public void testGetModel() throws Exception {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        signUpAction.setUser(user);

        // Execute the action
        UserDetailsDto result = signUpAction.getModel();
    }

    @Test
    public void testSignUpSuccess() throws Exception {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        signUpAction.setUser(user);

        // Mock userService behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        // Execute the action
        String result = signUpAction.signUp();

        // Verify interactions and assert result
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("Expected SUCCESS result", ActionSupport.SUCCESS, result);
    }

    @Test
    public void testSignUpEmailExists() throws Exception {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("existing@example.com");
        user.setPassword("password");
        signUpAction.setUser(user);

        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");

        // Mock userService behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user1);
        // Execute the action
        String result = signUpAction.signUp();

        // Verify interactions and assert result
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        assertEquals("Expected INPUT result", ActionSupport.INPUT, result);
    }

    @Test
    public void testSignUpError() throws Exception {
        // Setup user details
        UserDetailsDto user = new UserDetailsDto();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        signUpAction.setUser(user);

        User user1 = new User();
        user1.setName("Test User");
        user1.setEmail("existing@example.com");
        user1.setPassword("password");

        // Mock userService behavior to throw an exception
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        when(passwordEncoder.encode(user.getPassword())).thenReturn("EncodedPassword");

        doThrow(new RuntimeException("Save failed")).when(userRepository).save(any(User.class));

        // Execute the action
        String result = signUpAction.signUp();

        // Verify interactions and assert result
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("Expected INPUT result", ActionSupport.INPUT, result);
    }
}
