package org.foxminded.botalov.utilities.validation;

import org.foxminded.botalov.model.lesson.LessonDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.Set;

public class ValidLessonTimeConstraintTest {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    public void testLessonTimeConstraint() {
        LessonDto lesson = new LessonDto();
        lesson.setStart(Instant.parse("2921-01-06T09:00:00Z"));
        lesson.setEnd(Instant.parse("2921-01-06T10:31:00Z"));
        lesson.setCourseId(1);
        lesson.setGroupId(1);
        lesson.setTeacherId(1);
        lesson.setClassroom(1);

        Set<ConstraintViolation<LessonDto>> constraints = validator.validate(lesson);

        ConstraintViolation<LessonDto> actual = constraints.stream().findFirst().get();

        Assertions.assertEquals(1, constraints.size());
        Assertions.assertEquals("lesson duration must be 90 minutes and end time must be later than start time",
                actual.getMessage());
    }
}
