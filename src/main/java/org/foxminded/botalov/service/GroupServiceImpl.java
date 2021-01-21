package org.foxminded.botalov.service;

import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.group.GroupDto;
import org.foxminded.botalov.repository.GroupRepository;
import org.foxminded.botalov.repository.specification.forgroup.GroupFilter;
import org.foxminded.botalov.repository.specification.forgroup.GroupSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final GroupMapper mapper;
    private final GroupSpecification groupSpecification;

    @Autowired
    public GroupServiceImpl(GroupRepository repository,
                            GroupMapper mapper,
                            GroupSpecification groupSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.groupSpecification = groupSpecification;
    }

    @Override
    public GroupDto save(GroupDto groupDto) {
        Group group = mapper.groupDtoToGroup(groupDto);

        group = repository.save(group);

        return mapper.groupToGroupDto(group);
    }

    @Override
    public GroupDto findById(Integer id) {
        Group group;

        group = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("group with id %s not found", id)));

        return mapper.groupToGroupDto(group);
    }

    @Override
    public List<GroupDto> findAll() {
        List<Group> groups = repository.findAll();

        groups.sort(Comparator.comparing(Group::getId));

        return mapper.groupToGroupDto(groups);
    }

    @Override
    public void update(GroupDto groupDto) {

        repository.save(mapper.groupDtoToGroup(groupDto));
    }

    @Override
    public void delete(GroupDto groupDto) {
        repository.delete(mapper.groupDtoToGroup(groupDto));
    }

    @Override
    public List<GroupDto> getFilteredGroups(GroupFilter filter) {
        Specification<Group> specification = groupSpecification.filterGroups(filter);

        return mapper.groupToGroupDto(repository.findAll(specification));
    }
}
