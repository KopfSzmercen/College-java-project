package app.exceptions;

import app.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badCredentialsException(BadCredentialsException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(NotFoundException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse forbiddenException(ForbiddenException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.FORBIDDEN).build();
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            validationErrors.put(field, message);
        });

        return ErrorResponse.builder()
                .message("One or more errors occurred with your request.")
                .validationErrors(validationErrors)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
