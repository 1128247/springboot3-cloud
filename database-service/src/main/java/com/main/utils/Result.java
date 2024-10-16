package com.main.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Result<T> implements Serializable {
  private final Integer statusCode;
  private final String message;
  private final T data;

  private static final  ObjectMapper objectMapper = new ObjectMapper();

  private Result(Integer statusCode, String message, T data) {
    this.statusCode = statusCode;
    this.message = message;
    this.data = data;
  }

  public static <T> String successJSONStr(T data, String message) throws JsonProcessingException {
    Result<T> result = new Result<T>(StatusCode.OK.getCode(), message, data);
    return objectMapper.writeValueAsString(result);
  }
  public static <T> Result<T> success(T data, String message){
    return new Result<>(StatusCode.OK.getCode(), message, data);
  }

  public static <T> String errorJSONStr(StatusCode statusCode, String message) throws JsonProcessingException {
    Result<T> result =  new Result<T>(statusCode.getCode(), message, null);
    return objectMapper.writeValueAsString(result);
  }
  public static <T> Result<T> error(StatusCode statusCode, String message){
    return new Result<T>(statusCode.getCode(), message, null);
  }
}

