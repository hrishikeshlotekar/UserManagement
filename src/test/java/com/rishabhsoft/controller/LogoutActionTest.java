package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.junit.StrutsTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class LogoutActionTest extends StrutsTestCase {

    @Mock
    private Map<String, Object> session;

    private LogoutAction logoutAction;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        logoutAction = new LogoutAction();
    }

    @Test
    public void testSessionClearedOnLogout() throws Exception {
        // Setup: Simulate a user session
        session = new HashMap<>();
        session.put("user", "Demo.user");
        logoutAction.withSession(session);
        String result = logoutAction.execute();

        // Verify: Ensure the session is cleared and the action returns SUCCESS
        assertEquals("Expected a SUCCESS result from the action", ActionSupport.SUCCESS, result);
        assertTrue("Session should be empty after logout", session.isEmpty());
    }

    @Test
    public void testLogoutActionReturnsSuccess() throws Exception {
        // Setup: Session is already empty
        session = new HashMap<>();

        logoutAction.withSession(session);

        String result = logoutAction.execute();

        // Verify: Ensure the action returns SUCCESS even if the session was already empty
        assertEquals("Expected the action to return 'SUCCESS'", ActionSupport.SUCCESS, result);
        assertTrue("Session should be empty after logout", session.isEmpty());
    }


}
