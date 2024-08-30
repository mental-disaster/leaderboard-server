package com.project.server.handlers;

import com.project.server.enums.ErrorEnum;
import com.project.server.dtos.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidError(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder("유효성 검사 실패: ");
        e.getBindingResult().getFieldErrors().forEach(error -> message.append("[")
                .append(error.getField())
                .append("] ")
                .append(error.getDefaultMessage())
                .append("; "));

        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode(ErrorEnum.INVALID_PARAMETER.getCode())
                .message(message.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidParameterError(InvalidParameterException e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            message = ErrorEnum.INVALID_PARAMETER.getMessage();
        }

        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode(ErrorEnum.INVALID_PARAMETER.getCode())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundError(EntityNotFoundException e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            message = ErrorEnum.NOT_FOUND_DATA.getMessage();
        }

        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode(ErrorEnum.NOT_FOUND_DATA.getCode())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnexpectedError(Exception e) {
        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode(ErrorEnum.UNEXPECTED.getCode())
                .message(ErrorEnum.UNEXPECTED.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
