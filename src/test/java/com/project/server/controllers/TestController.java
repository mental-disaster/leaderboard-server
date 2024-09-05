package com.project.server.controllers;

import com.project.server.dtos.RecordPostDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/invalid-error")
    public void invalidError(@Valid @RequestBody RecordPostDto doNothing) {
    }

    @GetMapping("/invalid-parameter-error")
    public void invalidParameterError() {
        throw new InvalidParameterException();
    }

    @GetMapping("/entity-not-found-error")
    public void entityNotFoundError() {
        throw new EntityNotFoundException();
    }

    @GetMapping("/unexpected-error")
    public void unexpectedError() {
        throw new RuntimeException();
    }
}
