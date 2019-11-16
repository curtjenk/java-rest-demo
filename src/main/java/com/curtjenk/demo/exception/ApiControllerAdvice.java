package com.curtjenk.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.jsonwebtoken.MalformedJwtException;

@ControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ BadCredentialsException.class })
    public @ResponseBody ApiError badCredentialsException(final BadCredentialsException e,
            final HttpServletRequest request) {
        return ApiError.builder().status(HttpStatus.UNAUTHORIZED.value()).error(e.getLocalizedMessage())
                .requestedURI(request.getRequestURI()).build();
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MalformedJwtException.class })
    public @ResponseBody ApiError malformedJwtException(final Exception e,
            final HttpServletRequest request) {
        return ApiError.builder().status(HttpStatus.BAD_REQUEST.value()).error(e.getLocalizedMessage())
                .requestedURI(request.getRequestURI()).build();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ Exception.class })
    public @ResponseBody ApiError exception(final Exception e,
            final HttpServletRequest request) {
        return ApiError.builder().status(HttpStatus.BAD_REQUEST.value()).error(e.getLocalizedMessage())
                .requestedURI(request.getRequestURI()).build();
    }
}