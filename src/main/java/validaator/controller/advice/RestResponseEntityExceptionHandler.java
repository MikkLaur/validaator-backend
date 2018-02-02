package validaator.controller.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "A unique field with this data already exists on the database";    //TODO clarify response. Does the DB have to be rolled back? https://stackoverflow.com/questions/2109476/how-to-handle-dataintegrityviolationexception-in-spring
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);  // I'm not sure which HttpStatus to return upon this exception
    }                                                                   // Definitely not a teapot tho
}
