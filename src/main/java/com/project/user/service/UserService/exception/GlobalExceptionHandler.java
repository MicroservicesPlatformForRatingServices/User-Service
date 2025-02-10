package com.project.user.service.UserService.exception;

import com.project.user.service.UserService.payload.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder().message(message)
                .success(false).status(HttpStatus.NOT_FOUND).build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header-for-testing","custom-value-for-testing");
        return new ResponseEntity<>(response,headers,HttpStatus.NOT_FOUND);
    }
}
