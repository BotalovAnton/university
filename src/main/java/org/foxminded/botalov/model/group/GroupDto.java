package org.foxminded.botalov.model.group;

import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;

import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

public class GroupDto {
    @Null(groups = CheckAddedEntity.class)
    @Positive(groups = CheckUpdatedEntity.class)
    private Integer id;

    @Size(min = 3, max = 10)
    private String name;

    public GroupDto() {
    }

    public GroupDto(Integer id) {
        this.id = id;
    }

    public GroupDto(String name) {
        this.name = name;
    }

    public GroupDto(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDto groupDto = (GroupDto) o;
        return Objects.equals(id, groupDto.id) &&
                Objects.equals(name, groupDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
