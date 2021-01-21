package org.foxminded.botalov.service;

import org.foxminded.botalov.model.department.DepartmentDto;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentFilter;

import java.util.List;

public interface DepartmentService {
    DepartmentDto save(DepartmentDto departmentDto);

    DepartmentDto findById(Integer id);

    List<DepartmentDto> findAll();

    void update(DepartmentDto departmentDto);

    void delete(DepartmentDto departmentDto);

    List<DepartmentDto> getFilteredDepartments(DepartmentFilter filter);
}
