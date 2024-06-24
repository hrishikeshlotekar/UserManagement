package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.struts2.action.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;
/**
 * It is responsible for handling the login request and authenticating the user.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LoginAction extends ActionSupport implements SessionAware {

    @NotNull
    @Email
    private String username;
    @NotNull
    @Size(min = 6)
    private String password;
    private Map<String, Object> session;

    private AuthenticationManager authenticationManager;

    @Autowired
    public LoginAction(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * It is responsible for storing the user details in the session.
     * @param session The session object.
     */
    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * It is responsible for authenticating the user and storing the user details in the session.
     * @return SUCCESS if the user is authenticated successfully, otherwise INPUT.
     */
    @Override
    @Validations(
            requiredFields = {
                    @RequiredFieldValidator(fieldName = "username"),
                    @RequiredFieldValidator(fieldName = "password")
            },
            stringLengthFields = {
                    @StringLengthFieldValidator(fieldName = "password", message = "Password is required.")
            },
            emails = {
                    @EmailValidator(fieldName = "username", message = "Please enter a valid email address.")
            })
    public String execute() {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Store the username or user details in the session after successful authentication
            session.put("user", username);
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Invalid username or password");
            return INPUT;
        }
    }


}
