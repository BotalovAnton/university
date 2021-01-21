package org.foxminded.botalov.utilities.validate;

import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.utilities.validate.annotation.ValidLessonTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;

public class ValidLessonTimeValidator implements ConstraintValidator<ValidLessonTime, LessonDto> {

    @Override
    public void initialize(ValidLessonTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(LessonDto value, ConstraintValidatorContext context) {
        int minutesInLesson = 90;
        int secondsInMinute = 60;
        int millisecondsInSecond = 1000;

        if(value.getStart().toEpochMilli() < Instant.now().toEpochMilli()) {
            return false;
        }

        if(value.getEnd().toEpochMilli() < value.getStart().toEpochMilli()) {
            return false;
        }

        long lessonDuration = value.getEnd().toEpochMilli() - value.getStart().toEpochMilli();

        return lessonDuration == minutesInLesson * secondsInMinute * millisecondsInSecond;
    }
}
