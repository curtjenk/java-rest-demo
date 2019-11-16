package com.curtjenk.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ BadCredentialsException.class })
    public @ResponseBody ApiError badCredentialsException(final BadCredentialsException e,
            final HttpServletRequest request) {
        ApiError ae = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(e.getLocalizedMessage())
                .requestedURI(request.getRequestURI())
                .build();
        return ae;
    }
    
}