package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.junit.StrutsTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginActionTest extends StrutsTestCase {

    @InjectMocks
    private LoginAction loginAction;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Map<String, Object> session = new HashMap<>();
        loginAction.setSession(session);
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        loginAction.withSession(new HashMap<>());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        loginAction.setUsername("validUser");
        loginAction.setPassword("validPassword");
        String result = loginAction.execute();

        assertEquals(ActionSupport.SUCCESS, result);
        assertNotNull(loginAction.getSession().get("user"));
    }

    @Test
    public void testFailedLogin() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Login failed"));

        loginAction.setUsername("invalidUser");
        loginAction.setPassword("invalidPassword");
        String result = loginAction.execute();

        assertEquals(ActionSupport.INPUT, result);
        assertNull(loginAction.getSession().get("user"));
    }

    @Test
    public void testSessionIntegrityAfterSuccessfulLogin() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        loginAction.setUsername("validUser");
        loginAction.setPassword("validPassword");
        loginAction.execute();  // Perform login

        Map<String, Object> session = loginAction.getSession();
        assertEquals("validUser", session.get("user"));  // Check if user is stored correctly
    }

    @Test
    public void testUnexpectedExceptionHandling() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new IllegalStateException("Unexpected error"));

        loginAction.setUsername("user");
        loginAction.setPassword("password");
        String result = loginAction.execute();

        assertEquals(ActionSupport.INPUT, result);  // Assuming you have a global exception handler setting this result
        assertTrue("Expect error message for unexpected exceptions", !loginAction.getActionErrors().isEmpty());
    }

    @Test
    public void testSecurityContextAfterLogin() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        loginAction.setUsername("secureUser");
        loginAction.setPassword("securePassword");
        loginAction.execute();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull("Security context should be updated with authentication", auth);
        assertSame("Authentication object should be the same as mocked", authentication, auth);
    }


}
