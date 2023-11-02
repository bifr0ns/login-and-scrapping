package com.scrap.util;

import com.scrap.model.response.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

  public static ResponseEntity<ResponseApi<Object>> createNewResponseApi(
          Object response, Object msgResponse, HttpStatus httpStatus
  ) {
    ResponseApi<Object> responseBody = ResponseApi.builder()
            .msgResponse(msgResponse.toString())
            .response(response)
            .build();

    return new ResponseEntity<>(
            responseBody, httpStatus
    );
  }
}
