package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.group.GroupDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto groupToGroupDto(Group group);

    Group groupDtoToGroup(GroupDto groupDto);

    List<GroupDto> groupToGroupDto(List<Group> group);

    List<Group> groupDtoToGroup(List<GroupDto> groupDto);
}
