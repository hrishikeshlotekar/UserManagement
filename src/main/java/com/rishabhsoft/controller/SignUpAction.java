package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rishabhsoft.dto.UserDetailsDto;
import com.rishabhsoft.service.UserService;
import lombok.*;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is responsible for handling the sign-up request and adding the user to the database.
 */
@Getter
@Setter
public class SignUpAction extends ActionSupport implements ModelDriven<UserDetailsDto> {

    private static final String SUCCESS = "success";
    private static final String INPUT = "input";
    private static final String REDIRECT_ACTION = "redirectAction";


    private transient UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserDetailsDto user = new UserDetailsDto();

    /**
     * It is responsible for returning the user details.
     * @return The user details.
     */
    @Override
    public UserDetailsDto getModel() {
        return user;
    }

    /**
     * It is responsible for adding the user to the database.
     * @return SUCCESS if the user is added successfully, otherwise INPUT.
     */
    @Action(value = "signUp", results = {
            @Result(name = SUCCESS, type = REDIRECT_ACTION, params = {"actionName", "login"}),
            @Result(name = INPUT, location = "/WEB-INF/jsp/signUp.jsp")
    })
    @Validations(
            requiredFields = {
                    @RequiredFieldValidator(fieldName = "name"),
                    @RequiredFieldValidator(fieldName = "email"),
                    @RequiredFieldValidator(fieldName = "password")
            },
            stringLengthFields = {
                    @StringLengthFieldValidator(fieldName = "password", minLength = "6", maxLength = "10", message = "Password must be between 6 and 20 characters."),
                    @StringLengthFieldValidator(fieldName = "name", minLength = "3", maxLength = "20", message = "Name must be between 3 and 20 characters.")
            },
            emails = {
                    @EmailValidator(fieldName = "email", message = "Please enter a valid email address.")
            }
    )
    public String signUp() {
        try {

            if (userService.findByEmail(user.getEmail()) != null) {
                addActionError("Email already exists");
                return INPUT;
            }
            // Additional checks and balances before saving the user
            userService.saveUser(user);
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error adding user: " + e.getMessage());
            return INPUT;
        }
    }


}
