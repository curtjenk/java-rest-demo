package com.curtjenk.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String requestedURI;
    private String error;
    private List<ApiSubError> subErrors;

//     @PostConstruct
//     public void init() {
//        timestamp = LocalDateTime.now();
//    }

}