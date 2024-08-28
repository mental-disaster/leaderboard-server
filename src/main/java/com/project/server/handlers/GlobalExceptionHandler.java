package com.project.server.handlers;

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
                .errorCode("4001")
                .message(message.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidParameterError(InvalidParameterException e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            message = "잘못된 요청 파라미터입니다.";
        }

        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode("4002")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundError(EntityNotFoundException e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            message = "데이터가 존재하지 않습니다.";
        }

        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode("4041")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnexpectedError(Exception e) {
        ErrorResponseDto errRes = ErrorResponseDto.builder()
                .errorCode("5000")
                .message("예상하지 못한 오류가 발생했습니다. 잠시후 다시 시도해주세요")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
