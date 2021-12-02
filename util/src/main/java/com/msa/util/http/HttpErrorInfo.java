package com.msa.util.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpErrorInfo {
  private ZonedDateTime timestamp;
  private String path;
  private HttpStatus httpStatus;
  private String message;

  public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
    this.timestamp = ZonedDateTime.now();
    this.httpStatus = httpStatus;
    this.path = path;
    this.message = message;
  }
}
