/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author domingos.fernando
 */
@ControllerAdvice
public class GenericExceptionAdvice
{

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundHandler(EntityNotFoundException ex)
    {
        System.err.println("Passou para retornar o erro Not Found");
        return ex.getMessage();
    }

//    @ExceptionHandler({java.lang.RuntimeException.class})
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public ResponseEntity<?> resolveForbidenException(Exception ex)
//    {
//        //
//        final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "error 12 occurred", ex.toString());
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
}
