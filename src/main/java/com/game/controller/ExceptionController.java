package com.game.controller;

import com.game.exception.BadRequestException;
import com.game.exception.ErrorResult;
import com.game.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResult badRequest(HttpServletRequest req, Exception ex) {
        return new ErrorResult().setExceptionName(ex.getClass().getCanonicalName());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResult notFound(HttpServletRequest req, Exception ex) {
        return new ErrorResult().setExceptionName(ex.getClass().getCanonicalName());
    }
}
