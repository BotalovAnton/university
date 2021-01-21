package org.foxminded.botalov.repository.specification.fordepartment;

import org.foxminded.botalov.model.department.Department;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DepartmentSpecification {

    public Specification<Department> filterDepartments(DepartmentFilter filter) {
        Specification<Department> specification = Specification.where(null);

        if (filter.getDepartmentId() != null && !filter.getDepartmentId().isEmpty()) {
            specification = specification.and(searchByDepartmentId(filter.getDepartmentId()));
        }

        if (filter.getDepartmentName() != null && !filter.getDepartmentName().isEmpty()) {
            specification = specification.and(searchByDepartmentName(filter.getDepartmentName()));
        }

        return specification;
    }

    private Specification<Department> searchByDepartmentId(String departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), departmentId);
    }

    private Specification<Department> searchByDepartmentName(String departmentName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), departmentName);
    }
}
