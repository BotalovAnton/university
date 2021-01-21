package org.foxminded.botalov.service.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String massage) {
        super(massage);
    }

    public EntityNotFoundException(String massage, Exception exception) {
        super(massage, exception);
    }
}
