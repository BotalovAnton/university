package org.foxminded.botalov.repository.specification.forteacher;

import org.foxminded.botalov.model.teacher.Teacher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeacherSpecification {

    public Specification<Teacher> filterTeachers(TeacherFilter filter) {
        Specification<Teacher> specification = Specification.where(null);

        if (filter.getTeacherId() != null && !filter.getTeacherId().isEmpty()) {
            specification = specification.and(searchById(filter.getTeacherId()));
        }

        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            specification = specification.and(searchByFirstName(filter.getFirstName()));
        }

        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            specification = specification.and(searchByLastName(filter.getLastName()));
        }

        if (filter.getDepartmentId() != null && !filter.getDepartmentId().isEmpty()) {
            specification = specification.and(searchByDepartmentId(filter.getDepartmentId()));
        }

        if (filter.getDepartmentName() != null && !filter.getDepartmentName().isEmpty()) {
            specification = specification.and(searchByDepartmentName(filter.getDepartmentName()));
        }

        return specification;
    }

    private Specification<Teacher> searchById(String teacherId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), teacherId);
    }

    private Specification<Teacher> searchByFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    private Specification<Teacher> searchByLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastName"), lastName);
    }

    private Specification<Teacher> searchByDepartmentId(String departmentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("department").get("id"), departmentId);
    }

    private Specification<Teacher> searchByDepartmentName(String departmentName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("department").get("name"), departmentName);
    }
}
