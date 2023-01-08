package ron.cyberstar.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

  @ExceptionHandler
  public void handleException(Exception ex) {
    log.error(ex.getMessage(), ex);
  }
}
