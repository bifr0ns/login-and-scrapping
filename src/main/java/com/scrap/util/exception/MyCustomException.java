package com.scrap.util.exception;

public class MyCustomException extends Exception {
  public MyCustomException() {
    super();  // Call the constructor of the parent class
  }

  public MyCustomException(String message) {
    super(message);  // Call the constructor of the parent class with a custom message
  }

  public MyCustomException(String message, Throwable cause) {
    super(message, cause);  // Call the constructor of the parent class with a custom message and a cause
  }
}

