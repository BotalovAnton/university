package org.foxminded.botalov.controller;

import org.foxminded.botalov.model.group.GroupDto;
import org.foxminded.botalov.repository.specification.forgroup.GroupFilter;
import org.foxminded.botalov.service.GroupService;
import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDto> getAll() {
        return groupService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders add(@Validated(value = CheckAddedEntity.class) @RequestBody GroupDto groupDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/group/%s", groupService.save(groupDto).getId())));

        return headers;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable int id) {
        groupService.delete(new GroupDto(id));
    }

    @GetMapping("/{id}")
    public GroupDto findById(@PathVariable int id) {
        return groupService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody GroupDto groupDto) {
        groupService.update(groupDto);
    }

    @GetMapping("/filter")
    public List<GroupDto> getFilteredGroups(GroupFilter groupFilter) {
        return groupService.getFilteredGroups(groupFilter);
    }
}
