package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.action.SessionAware;

import java.util.Map;

/**
 * It is responsible for handling the logout request and clearing the session.
 */
public class LogoutAction extends ActionSupport implements SessionAware {

    private Map<String, Object> session;

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String execute() {
        session.clear();  // Clearing the session on logout
        return SUCCESS;
    }

}
