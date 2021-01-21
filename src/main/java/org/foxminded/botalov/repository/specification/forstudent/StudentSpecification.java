package org.foxminded.botalov.repository.specification.forstudent;

import org.foxminded.botalov.model.student.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StudentSpecification {

    public Specification<Student> filterStudents(StudentFilter filter) {
        Specification<Student> specification = Specification.where(null);

        if (filter.getStudentId() != null && !filter.getStudentId().isEmpty()) {
            specification = specification.and(searchByStudentId(filter.getStudentId()));
        }

        if (filter.getStudentFirstName() != null && !filter.getStudentFirstName().isEmpty()) {
            specification = specification.and(searchByFirstName(filter.getStudentFirstName()));
        }

        if (filter.getStudentLastName() != null && !filter.getStudentLastName().isEmpty()) {
            specification = specification.and(searchByLastName(filter.getStudentLastName()));
        }

        if (filter.getGroupId() != null && !filter.getGroupId().isEmpty()) {
            specification = specification.and(searchByGroupId(filter.getGroupId()));
        }

        if (filter.getGroupName() != null && !filter.getGroupName().isEmpty()) {
            specification = specification.and(searchByGroupName(filter.getGroupName()));
        }

        return specification;
    }


    private Specification<Student> searchByStudentId(String studentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), studentId);
    }

    private Specification<Student> searchByFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    private Specification<Student> searchByLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastName"), lastName);
    }

    private Specification<Student> searchByGroupId(String groupId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("group").get("id"), groupId);
    }

    private Specification<Student> searchByGroupName(String groupName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("group").get("name"), groupName);
    }
}
