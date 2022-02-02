package com.connor.csprojectapi.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class WebExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(WebException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    HashMap<String, Object> webException(WebException exception) {
        return exception.getData();
    }

}
