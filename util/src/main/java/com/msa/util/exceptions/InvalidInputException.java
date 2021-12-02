package com.msa.util.exceptions;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
public class InvalidInputException extends RuntimeException {
  public InvalidInputException() {
  }

  public InvalidInputException(String message) {
    super(message);
  }

  public InvalidInputException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidInputException(Throwable cause) {
    super(cause);
  }
}
