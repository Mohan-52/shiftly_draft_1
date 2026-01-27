package com.mohan.shiftly.exception;

import com.mohan.shiftly.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, Exception ex, String path){
        ErrorResponseDto response=new ErrorResponseDto();
        response.setTimeStamp(LocalDateTime.now());
        response.setPath(path);
        response.setError(status.getReasonPhrase());
        response.setStatus(status.value());
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistsEx.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExistsEx(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.CONFLICT,ex,request.getRequestURI());
    }

    @ExceptionHandler(InvalidCrenentialEx.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredEx(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.UNAUTHORIZED,ex,request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundEx.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundEx(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.NOT_FOUND,ex,request.getRequestURI());

    }

    @ExceptionHandler(ClockOutEx.class)
    public ResponseEntity<ErrorResponseDto> handleClockOutEx(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.BAD_REQUEST,ex,request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(AccessDeniedException ex,
                                                               HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex, request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllArgEx(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.BAD_REQUEST,ex,request.getRequestURI());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAnyEx(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex,request.getRequestURI());
    }

}
