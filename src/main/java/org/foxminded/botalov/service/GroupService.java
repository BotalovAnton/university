package org.foxminded.botalov.service;

import org.foxminded.botalov.model.group.GroupDto;
import org.foxminded.botalov.repository.specification.forgroup.GroupFilter;

import java.util.List;

public interface GroupService {
    GroupDto save(GroupDto groupDto);

    GroupDto findById(Integer id);

    List<GroupDto> findAll();

    void update(GroupDto groupDto);

    void delete(GroupDto groupDto);

    List<GroupDto> getFilteredGroups(GroupFilter filter);
}
