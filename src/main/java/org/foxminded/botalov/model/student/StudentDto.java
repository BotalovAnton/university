package org.foxminded.botalov.model.student;

import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;

import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

public class StudentDto {
    @Null(groups = CheckAddedEntity.class)
    @Positive(groups = CheckUpdatedEntity.class)
    private Integer id;

    @Size(min = 3, max = 15)
    private String firstName;

    @Size(min = 3, max = 15)
    private String lastName;

    @Positive
    private int groupId;

    private String groupName;

    public StudentDto() {
    }

    public StudentDto(Integer id) {
        this.id = id;
    }

    public StudentDto(String firstName, String lastName, int groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public StudentDto(Integer id, String firstName, String lastName, int groupId, String groupName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
        this.groupName = groupName;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return groupId == that.groupId && Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, groupId);
    }
}
