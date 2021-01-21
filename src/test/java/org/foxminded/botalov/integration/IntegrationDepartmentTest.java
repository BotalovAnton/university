package org.foxminded.botalov.integration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.department.DepartmentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/test_data.sql")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationDepartmentTest {
    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    private final List<DepartmentDto> departments = List.of(
            new DepartmentDto(1, "department1"),
            new DepartmentDto(2, "department2")
    );

    @Test
    public void addDepartment() throws Exception {
        DepartmentDto department = new DepartmentDto("department3");

        mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/department/3"));
    }

    @Test
    public void getAllDepartments() throws Exception {
        mockMvc.perform(get("/department").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(departments)));
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/department/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(departments.get(0))));
    }

    @Test
    public void updateDepartment() throws Exception {
        DepartmentDto department = new DepartmentDto(1, "department3");

        mockMvc.perform(put("/department/1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(department)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteDepartment() throws Exception {
        mockMvc.perform(delete("/department/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getByIdShouldReturn404IfDepartmentWithIdNotFound() throws Exception {
        mockMvc.perform(get("/department/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("department with id 3 not found"));
    }

    @Test
    public void getFilteredDepartments() throws Exception {
        mockMvc.perform(get("/department/filter").accept(MediaType.APPLICATION_JSON)
        .param("departmentName", "department1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(Collections.singletonList(departments.get(0)))));
    }

    @Test
    public void code400ShouldReturnIfValidationError() throws Exception {
        DepartmentDto department = departments.get(0);
        department.setId(null);
        department.setName("de");

        mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(department)))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("size must be between 3 and 30"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void code400ShouldReturnWhenDatabaseConstraintIsViolated() throws Exception {
        DepartmentDto department = departments.get(0);
        department.setId(null);

        mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(department)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("department with the specified name already exists"));
    }
}
