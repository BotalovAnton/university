package org.foxminded.botalov.repository.specification.forcourse;


import org.foxminded.botalov.model.course.Course;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CourseSpecification {

    public Specification<Course> filterCourses(CourseFilter filter) {
        Specification<Course> specification = Specification.where(null);

        if (filter.getCourseId() != null && !filter.getCourseId().isEmpty()) {
            specification = specification.and(searchById(filter.getCourseId()));
        }

        if (filter.getCourseName() != null && !filter.getCourseName().isEmpty()) {
            specification = specification.and(searchByName(filter.getCourseName()));
        }

        if (filter.getDepartmentId() != null && !filter.getDepartmentId().isEmpty()) {
            specification = specification.and(searchByDepartmentId(filter.getDepartmentId()));
        }

        if (filter.getDepartmentName() != null && !filter.getDepartmentName().isEmpty()) {
            specification = specification.and(searchByDepartmentName(filter.getDepartmentName()));
        }

        return specification;
    }

    private Specification<Course> searchById(String courseId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), courseId);
    }

    private Specification<Course> searchByName(String courseName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), courseName);
    }

    private Specification<Course> searchByDepartmentId(String departmentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("department").get("id"), departmentId);
    }

    private Specification<Course> searchByDepartmentName(String departmentName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("department").get("name"), departmentName);
    }
}
