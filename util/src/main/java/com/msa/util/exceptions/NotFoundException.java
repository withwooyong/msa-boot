package com.msa.util.exceptions;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException() {
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }
}