package ru.vksychev.cart.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlingController {

    /**
     *  Возвращение CONFLICT статуса после DataIntegrityViolationException
     */
    @ResponseStatus(value= HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict(){
    }
}
