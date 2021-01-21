package org.foxminded.botalov.model.course;

import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;

import javax.validation.constraints.*;
import java.util.Objects;

public class CourseDto {
    @Null(groups = CheckAddedEntity.class)
    @Positive(groups = CheckUpdatedEntity.class)
    private Integer id;

    @Size(min = 3, max = 30)
    @NotNull
    private String name;

    @Positive
    private int departmentId;
    private String departmentName;

    public CourseDto() {
    }

    public CourseDto(Integer id) {
        this.id = id;
    }

    public CourseDto(String name, int departmentId, String departmentName) {
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public CourseDto(Integer id, String name, int departmentId) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
    }

    public CourseDto(Integer id, String name, int departmentId, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(id, courseDto.id) &&
                Objects.equals(name, courseDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, departmentId);
    }
}
