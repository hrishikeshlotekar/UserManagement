package com.rishabhsoft.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rishabhsoft.dto.UserDetailsDto;
import com.rishabhsoft.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * It is responsible for handling the user management request and managing the users.
 */
@Getter
@Setter
public class UserManagementAction extends ActionSupport implements ModelDriven<UserDetailsDto>  {

    private static final String SUCCESS = "success";
    private static final String INPUT = "input";
    private static final String REDIRECT_ACTION = "redirectAction";

    private transient UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserDetailsDto user = new UserDetailsDto();
    private List<UserDetailsDto> users;
    private int page = 0; // Default to first page
    private int size = 10; // Default page size
    private int totalPages;

    /**
     * It is responsible for returning the user details.
     * @return The user details.
     */
    @Override
    public UserDetailsDto getModel() {
        return user;
    }

    /**
     * It is responsible for displaying the user management page.
     * @return SUCCESS.
     */
    @Action(value = "display", results = {
            @Result(name = SUCCESS, location = "/WEB-INF/jsp/userManagement.jsp")
    })
    public String display() {
        return SUCCESS;
    }

    /**
     * It is responsible for listing the users.
     * @return SUCCESS if the users are listed successfully, otherwise ERROR.
     */
    @Action(value = "listUsers", results = {
            @Result(name = SUCCESS, location = "/WEB-INF/jsp/userManagement.jsp")
    })
    public String listUsers() {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserDetailsDto> userPage = userService.findAllUsers(pageable);
            users = userPage.getContent();
            totalPages = userPage.getTotalPages();
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error listing users: " + e.getMessage());
            return ERROR;
        }
    }

    /**
     * It is responsible for adding a user.
     * @return SUCCESS if the user is added successfully, otherwise INPUT.
     */
    @Action(value = "addUser", results = {
            @Result(name = SUCCESS, type = REDIRECT_ACTION, params = {"actionName", "listUsers"}),
            @Result(name = INPUT, location = "/WEB-INF/jsp/addUser.jsp")
    })
    public String addUser() {
        try {

            if (userService.findByEmail(user.getEmail()) != null) {
                addActionError("Email already exists");
                return INPUT;
            }
            // Additional checks and balances before saving the user
            userService.saveUser(user);
            return SUCCESS;
        } catch (Exception e) {
            return INPUT;
        }
    }

    /**
     * It is responsible for deleting a user.
     * @return SUCCESS if the user is deleted successfully, otherwise ERROR.
     */
    @Action(value = "deleteUser", results = {
            @Result(name = SUCCESS, type = REDIRECT_ACTION, params = {"actionName", "listUsers"})
    })
    public String deleteUser() {
        try {
            userService.deleteUser(user.getId());
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error deleting user: " + e.getMessage());
            return ERROR;
        }
    }
}
