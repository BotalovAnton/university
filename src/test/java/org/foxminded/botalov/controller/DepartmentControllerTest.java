package org.foxminded.botalov.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.department.DepartmentDto;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentFilter;
import org.foxminded.botalov.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService service;

    private final JsonMapper mapper = new JsonMapper();


    private final int departmentId = 1;
    private final String departmentName = "department1";

    @Test
    public void add() throws Exception {
        Mockito.when(service.save(new DepartmentDto(departmentName))).thenReturn(new DepartmentDto(departmentId, departmentName));

        mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new DepartmentDto(departmentName))))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/department/" + departmentId))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).save(new DepartmentDto(departmentName));
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(departmentId)).thenReturn(new DepartmentDto(departmentId));

        mockMvc.perform(get("/department/" + departmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(new DepartmentDto(departmentId))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(departmentId);
    }

    @Test
    public void getAll() throws Exception {
        List<DepartmentDto> departments = List.of(new DepartmentDto(1), new DepartmentDto(2));

        Mockito.when(service.findAll()).thenReturn(departments);

        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(departments)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void update() throws Exception {
        mockMvc.perform(put("/department/" + departmentId)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(new DepartmentDto(departmentId))))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).update(new DepartmentDto(departmentId));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/department/" + departmentId))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).delete(new DepartmentDto(departmentId));
    }

    @Test
    public void getFilteredDepartments() throws Exception {

        Mockito.when(service.getFilteredDepartments(any(DepartmentFilter.class))).thenReturn(Collections.singletonList(new DepartmentDto(departmentId)));

        mockMvc.perform(get("/department/filter")
                .param("departmentId", "1")
                .param("departmentName", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(Collections.singletonList(new DepartmentDto(departmentId)))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).getFilteredDepartments(any(DepartmentFilter.class));
    }
}
