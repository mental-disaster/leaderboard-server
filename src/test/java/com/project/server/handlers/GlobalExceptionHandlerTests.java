package com.project.server.handlers;

import com.project.server.controllers.TestController;
import com.project.server.enums.ErrorEnum;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.InvalidParameterException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
public class GlobalExceptionHandlerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInvalidError () throws Exception {
        mockMvc.perform(get("/test/invalid-error")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorEnum.INVALID_PARAMETER.getCode()))
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    Assertions.assertInstanceOf(MethodArgumentNotValidException.class, exception);
                });
    }

    @Test
    public void testInvalidParameterError () throws Exception {
        mockMvc.perform(get("/test/invalid-parameter-error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorEnum.INVALID_PARAMETER.getCode()))
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    Assertions.assertInstanceOf(InvalidParameterException.class, exception);
                });
    }

    @Test
    public void testEntityNotFoundError () throws Exception {
        mockMvc.perform(get("/test/entity-not-found-error"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(ErrorEnum.NOT_FOUND_DATA.getCode()))
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    Assertions.assertInstanceOf(EntityNotFoundException.class, exception);
                });
    }

    @Test
    public void testUnexpectedError () throws Exception {
        mockMvc.perform(get("/test/unexpected-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value(ErrorEnum.UNEXPECTED.getCode()))
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    Assertions.assertInstanceOf(RuntimeException.class, exception);
                });
    }
}
