package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.group.GroupDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(GroupMapperImpl.class)
public class GroupMapperTest {
    private final GroupMapper mapper;
    private final int groupId = 1;
    private final String groupName = "group";
    @Autowired
    public GroupMapperTest(GroupMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void groupToGroupDto() {
        Group group = new Group(groupId, groupName);
        GroupDto expected = new GroupDto(groupId, groupName);
        GroupDto actual = mapper.groupToGroupDto(group);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void groupDtoToGroup() {
        GroupDto groupDto = new GroupDto(groupId, groupName);
        Group expected = new Group(groupId, groupName);
        Group actual = mapper.groupDtoToGroup(groupDto);

        Assertions.assertEquals(expected, actual);
    }
}
