package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.model.department.DepartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(DepartmentMapperImpl.class)
public class DepartmentMapperTest {
    private final DepartmentMapper mapper;
    private final int departmentId = 1;
    private final String departmentName = "department";

    @Autowired
    public DepartmentMapperTest(DepartmentMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void departmentToDepartmentDto() {
        Department department = new Department(departmentId, departmentName);
        DepartmentDto expected = new DepartmentDto(departmentId, departmentName);
        DepartmentDto actual = mapper.departmentToDepartmentDto(department);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void departmentDtoToDepartment() {
        DepartmentDto departmentDTO = new DepartmentDto(departmentId, departmentName);
        Department expected = new Department(departmentId, departmentName);
        Department actual = mapper.departmentDtoToDepartment(departmentDTO);

        Assertions.assertEquals(expected, actual);
    }
}
