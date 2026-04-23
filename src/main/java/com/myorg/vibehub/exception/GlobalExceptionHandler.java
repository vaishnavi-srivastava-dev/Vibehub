package com.myorg.vibehub.exception;

import com.myorg.vibehub.dto.response.GenericResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto> handleException(Exception e){
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setIsSuccess(false);
        genericResponseDto.setMessage("An exception Occurred!");
        genericResponseDto.setDetails(Map.of("details : ",e.getMessage()));

        //400- Bad Request
        return new ResponseEntity<>(genericResponseDto, HttpStatusCode.valueOf(400));
    }

    //You may make whatever Custom Exception Classes you want but since the annotations-
    //@ExceptionHandler and @ControllerAdvice is used here, all exceptions must be handled here.
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GenericResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setIsSuccess(false);
        genericResponseDto.setMessage("User doesn't exist");
        genericResponseDto.setDetails(Map.of("detail", ex.getMessage()));

        return new ResponseEntity<>(genericResponseDto, HttpStatusCode.valueOf(404));
    }
}
