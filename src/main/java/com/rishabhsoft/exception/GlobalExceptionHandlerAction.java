package com.rishabhsoft.exception;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;

/**
 * It is responsible for handling the global exception
 */
@Getter
@Setter
public class GlobalExceptionHandlerAction extends ActionSupport {

    private String errorMessage;

    public String handleException() {
        return ERROR;
    }
}
