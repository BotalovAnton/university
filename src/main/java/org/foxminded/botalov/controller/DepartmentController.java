package org.foxminded.botalov.controller;

import org.foxminded.botalov.model.department.DepartmentDto;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentFilter;
import org.foxminded.botalov.service.DepartmentService;
import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<DepartmentDto> getAll() {
        return departmentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders add(@Validated(value = CheckAddedEntity.class) @RequestBody DepartmentDto departmentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/department/%s", departmentService.save(departmentDto).getId())));

        return headers;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") int id) {
        departmentService.delete(new DepartmentDto(id));
    }

    @GetMapping("/{id}")
    public DepartmentDto findById(@Positive @PathVariable("id") int id) {
        return departmentService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(value = CheckUpdatedEntity.class) @RequestBody DepartmentDto departmentDto) {
        departmentService.update(departmentDto);
    }

    @GetMapping("/filter")
    public List<DepartmentDto> getFilteredDepartments(DepartmentFilter departmentFilter) {
        return departmentService.getFilteredDepartments(departmentFilter);
    }
}
