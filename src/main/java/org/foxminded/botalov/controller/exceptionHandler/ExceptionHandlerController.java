package org.foxminded.botalov.controller.exceptionHandler;

import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Autowired
    Environment environment;

    Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    private final Map<String, String> constraints = Map.of(
            "departments_name_key", "department with the specified name already exists",
            "courses_name_key", "course with the specified name already exists",
            "groups_name_key", "group with the specified name already exists",
            "department_id_fkey", "the department with the specified id does not exist",
            "group_id_fkey", "the group with the specified id does not exist",
            "course_id_fkey", "the course with the specified id does not exist",
            "teacher_id_fkey", "teacher with the specified id does not exist");

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleDatabaseException(EntityNotFoundException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return new ResponseEntity<>(exception.getMessage(), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(DataIntegrityViolationException exception) {
        String constraintName = getConstraintName(exception);

        String message = constraints.getOrDefault(constraintName,
                constraints.getOrDefault(
                        constraintName, exception.getMessage()
                ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception exception) {
        logger.error(exception.getMessage(), exception);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        String errorMessage = "an unexpected error has occurred, contact your system administrator";

        return new ResponseEntity<>(errorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String validationMessage = ex.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("\n"));

        headers.setContentType(MediaType.TEXT_PLAIN);

        return new ResponseEntity<>(validationMessage, headers, HttpStatus.BAD_REQUEST);
    }

    private String getConstraintName(DataIntegrityViolationException exception) {
        ConstraintViolationException constraintException = (ConstraintViolationException) exception.getCause();

        String unprocessedConstraintName = constraintException.getConstraintName();

        if (environment != null && Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            return unprocessedConstraintName
                    .substring(unprocessedConstraintName.indexOf("."), unprocessedConstraintName.indexOf("_INDEX"))
                    .replace(".", "");
        } else {
            return unprocessedConstraintName;
        }
    }
}
