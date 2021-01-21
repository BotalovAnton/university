package org.foxminded.botalov.integration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.teacher.TeacherDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("/test_data.sql")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTeacherTest {

    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    private final List<TeacherDto> teachers = List.of(
            new TeacherDto(1, "Ivan", "Ivanov", 1, "department1"),
            new TeacherDto(2, "Petr", "Petrov", 1, "department1"),
            new TeacherDto(3, "Andrey", "Andreev", 2, "department2")
    );

    @Test
    public void findById() throws Exception {

        mockMvc.perform(get("/teacher/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(teachers.get(0)))).andReturn();
    }

    @Test
    public void getAllTeachers() throws Exception {

        mockMvc.perform(get("/teacher").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(teachers)));
    }

    @Test
    public void addTeacher() throws Exception {
        TeacherDto student = teachers.get(0);
        student.setId(null);
        student.setFirstName("Sergey");
        student.setLastName("Sergeev");

        mockMvc.perform(post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/teacher/4"));
    }

    @Test
    public void deleteTeacher() throws Exception {
        mockMvc.perform(delete("/teacher/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateTeacher() throws Exception {
        TeacherDto student = teachers.get(0);
        student.setFirstName("Sergey");

        mockMvc.perform(put("/teacher/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getFilteredTeachers() throws Exception {
        List<TeacherDto> studentDtos = teachers.stream()
                .filter(student -> student.getDepartmentId() == 1).collect(Collectors.toList());

        mockMvc.perform(get("/teacher/filter")
                .param("departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(studentDtos)));
    }

    @Test
    public void getByIdShouldReturn404IfTeacherWithIdNotFound() throws Exception {
        mockMvc.perform(get("/teacher/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("teacher with id 4 not found"));
    }

    @Test
    public void code400ShouldReturnIfValidationError() throws Exception {
        TeacherDto teacher = teachers.get(0);
        teacher.setId(null);
        teacher.setFirstName("Iv");

        mockMvc.perform(post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(teacher)))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("size must be between 3 and 15"))
                .andExpect(status().isBadRequest());
    }
}
