package org.foxminded.botalov.service;

import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.group.GroupDto;
import org.foxminded.botalov.repository.GroupRepository;
import org.foxminded.botalov.repository.specification.forgroup.GroupFilter;
import org.foxminded.botalov.repository.specification.forgroup.GroupSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.GroupMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class GroupServiceImplTest {
    private final GroupRepository repository = Mockito.mock(GroupRepository.class);
    private final GroupMapper mapper = Mockito.mock(GroupMapper.class);
    private final GroupSpecification groupSpecification = Mockito.mock(GroupSpecification.class);
    private final GroupService service = new GroupServiceImpl(repository, mapper, groupSpecification);

    private final int groupId = 1;
    private final String groupName = "group";

    @Test
    public void save() {

        Mockito.when(mapper.groupDtoToGroup(new GroupDto(groupName)))
                .thenReturn(new Group(groupName));

        Mockito.when(mapper.groupToGroupDto(new Group(groupId, groupName)))
                .thenReturn(new GroupDto(groupId, groupName));

        service.save(new GroupDto(groupName));

        Mockito.verify(repository, Mockito.times(1))
                .save(new Group(groupName));

        Mockito.verify(mapper, Mockito.times(1))
                .groupDtoToGroup(new GroupDto(groupName));
    }

    @Test
    public void findById() {
        Mockito.when(mapper.groupToGroupDto(new Group(groupId, groupName)))
                .thenReturn(new GroupDto(groupId, groupName));

        Mockito.when(repository.findById(groupId)).thenReturn(Optional.ofNullable(new Group(groupId, groupName)));

        GroupDto actual = service.findById(groupId);

        Mockito.verify(mapper, Mockito.times(1))
                .groupToGroupDto(new Group(groupId, groupName));

        Mockito.verify(repository, Mockito.times(1)).findById(groupId);
        Assertions.assertEquals(new GroupDto(groupId, groupName), actual);
    }

    @Test
    public void findAll() {
        int group1Id = 1;
        int group2Id = 2;
        List<GroupDto> expected = Arrays.asList(new GroupDto(group1Id), new GroupDto(group2Id));
        List<Group> returned = Arrays.asList(new Group(group1Id), new Group(group2Id));
        List<GroupDto> mapped = Arrays.asList(new GroupDto(group1Id), new GroupDto(group2Id));

        Mockito.when(mapper.groupToGroupDto(returned)).thenReturn(mapped);
        Mockito.when(repository.findAll()).thenReturn(returned);

        List<GroupDto> actual = service.findAll();

        Mockito.verify(mapper, Mockito.times(1)).groupToGroupDto(returned);
        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void update() {
       Mockito.when(mapper.groupDtoToGroup(new GroupDto(groupId, groupName)))
                .thenReturn(new Group(groupId, groupName));

        service.update(new GroupDto(groupId, groupName));

        Mockito.verify(repository, Mockito.times(1))
                .save(new Group(groupId, groupName));

        Mockito.verify(mapper, Mockito.times(1))
                .groupDtoToGroup(new GroupDto(groupId, groupName));
    }

    @Test
    public void delete() {
        Mockito.when(mapper.groupDtoToGroup(new GroupDto(groupId, groupName)))
                .thenReturn(new Group(groupId, groupName));

        service.delete(new GroupDto(groupId, groupName));

        Mockito.verify(repository, Mockito.times(1))
                .delete(new Group(groupId, groupName));

        Mockito.verify(mapper, Mockito.times(1))
                .groupDtoToGroup(new GroupDto(groupId, groupName));
    }

    @Test
    public  void findByIdShouldThrowExceptionIfIdNotExist() {
        Mockito.when(repository.findById(groupId)).thenReturn(Optional.ofNullable(null));

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(groupId));
        Assertions.assertEquals(String.format("group with id %s not found", groupId), throwable.getMessage());
    }

    @Test
    public void getFilteredGroups() {
        Mockito.when(groupSpecification.filterGroups(any(GroupFilter.class))).thenReturn(Specification.where(null));

        service.getFilteredGroups(new GroupFilter());

        Mockito.verify(groupSpecification, Mockito.times(1)). filterGroups(any(GroupFilter.class));
        Mockito.verify(repository, Mockito.times(1)).findAll(Specification.where(null));
        Mockito.verify(mapper, Mockito.times(1)).groupToGroupDto(any(List.class));
    }
}
