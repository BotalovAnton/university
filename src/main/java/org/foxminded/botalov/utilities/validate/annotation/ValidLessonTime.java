package org.foxminded.botalov.utilities.validate.annotation;

import org.foxminded.botalov.utilities.validate.ValidLessonTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated {@code LessonDto} must contain the start and end time fields of type {@code Instant}
 * start time must be greater than end time
 * start time and end time must be later than the current time
 * the difference between the end time and the start time must be 90 minutes
 *
 * Supported types {@code LessonDto}
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLessonTimeValidator.class)
public @interface ValidLessonTime {

    String message() default "{validLessonTime.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
