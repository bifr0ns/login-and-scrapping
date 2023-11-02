package com.scrap.util.exception;

import com.scrap.model.response.ResponseError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ResponseEntityHandling {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ResponseError> handleAllException(Throwable ex, WebRequest request) {

    ResponseError error = ResponseError.builder()
            .mensaje(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .detalles(Collections.singletonList(ex.getMessage())).build();

    return new ResponseEntity<>(
            error
            , HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseError> handleEntityNotFoundException(EntityNotFoundException ex) {

    ResponseError error = ResponseError.builder()
            .mensaje(HttpStatus.NOT_FOUND.getReasonPhrase())
            .detalles(List.of(ex.getMessage())).build();

    return new ResponseEntity<>(
            error
            , HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MyCustomException.class)
  public final ResponseEntity<ResponseError> handleMyCustomException(Throwable ex, WebRequest request) {

    ResponseError error = ResponseError.builder()
            .mensaje(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .detalles(Collections.singletonList(ex.getMessage())).build();

    return new ResponseEntity<>(
            error
            , HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
