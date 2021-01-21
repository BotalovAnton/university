package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.model.department.DepartmentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDto departmentToDepartmentDto(Department department);

    Department departmentDtoToDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> departmentToDepartmentDto(List<Department> department);

    List<Department> departmentDtoToDepartment(List<DepartmentDto> departmentDto);
}
