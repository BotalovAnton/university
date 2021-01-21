package org.foxminded.botalov.repository.specification.forlesson;

import org.foxminded.botalov.model.lesson.Lesson;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LessonSpecification {

    public Specification<Lesson> filterLessons(LessonFilter filter) {
        Specification<Lesson> specification = Specification.where(null);

        if (filter.getLessonId() != null && !filter.getLessonId().isEmpty()) {
            specification = specification.and(searchByLessonId(filter.getLessonId()));
        }

        if (filter.getMinStartTime() != null && !filter.getMinStartTime().isEmpty()) {
            specification = specification.and(searchAfterTime(filter.getMinStartTime()));
        }

        if (filter.getMaxStartTime() != null && !filter.getMaxStartTime().isEmpty()) {
            specification = specification.and(searchBeforeTime(filter.getMaxStartTime()));
        }

        if (filter.getClassroom() != null && !filter.getClassroom().isEmpty()) {
            specification = specification.and(searchByClassroom(filter.getClassroom()));
        }

        if (filter.getCourseId() != null && !filter.getCourseId().isEmpty()) {
            specification = specification.and(searchByCourseId(filter.getCourseId()));
        }

        if (filter.getGroupId() != null && !filter.getGroupId().isEmpty()) {
            specification = specification.and(searchByGroupId(filter.getGroupId()));
        }

        if (filter.getTeacherId() != null && !filter.getTeacherId().isEmpty()) {
            specification = specification.and(searchByTeacherId(filter.getTeacherId()));
        }

        return specification;
    }

    private Specification<Lesson> searchByLessonId(String lessonId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), lessonId);
    }

    private Specification<Lesson> searchAfterTime(String time) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get("start"), Instant.parse(time));
    }

    private Specification<Lesson> searchBeforeTime(String time) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("start"), Instant.parse(time));
    }

    private Specification<Lesson> searchByClassroom(String classroom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("classroom"), classroom);
    }

    private Specification<Lesson> searchByCourseId(String courseId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("course").get("id"), courseId);
    }

    private Specification<Lesson> searchByGroupId(String groupId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("group").get("id"), groupId);
    }

    private Specification<Lesson> searchByTeacherId(String teacherId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("teacher").get("id"), teacherId);
    }
}
