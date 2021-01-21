package org.foxminded.botalov.model.teacher;

import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;

import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TeacherDto {
    @Null(groups = CheckAddedEntity.class)
    @Positive(groups = CheckUpdatedEntity.class)
    private Integer id;

    @Size(min = 3, max = 15)
    private String firstName;

    @Size(min = 3, max = 15)
    private String lastName;

    @Positive
    private int departmentId;

    private String departmentName;

    public TeacherDto() {
    }

    public TeacherDto(Integer id) {
        this.id = id;
    }

    public TeacherDto(String firstName, String lastName, int departmentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
    }

    public TeacherDto(Integer id, String firstName, String lastName, int departmentId, String departmentName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        TeacherDto that = (TeacherDto) o;
        return departmentId == that.departmentId && Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, departmentId);
    }
}
