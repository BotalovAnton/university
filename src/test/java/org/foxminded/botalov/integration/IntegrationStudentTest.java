package org.foxminded.botalov.integration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.student.StudentDto;
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
public class IntegrationStudentTest {
    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    private final List<StudentDto> students = List.of(
            new StudentDto(1, "Ivan", "Ivanov", 1, "group1"),
            new StudentDto(2, "Petr", "Petrov", 1, "group1"),
            new StudentDto(3, "Andrey", "Andreev", 2, "group2")
    );

    @Test
    public void findById() throws Exception {

        mockMvc.perform(get("/student/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(students.get(0)))).andReturn();
    }

    @Test
    public void getAllStudents() throws Exception {

        mockMvc.perform(get("/student").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(students)));
    }

    @Test
    public void addStudent() throws Exception {
        StudentDto student = students.get(0);
        student.setId(null);
        student.setFirstName("Sergey");
        student.setLastName("Sergeev");

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/student/4"));
    }

    @Test
    public void deleteStudent() throws Exception {
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateStudent() throws Exception {
        StudentDto student = students.get(0);
        student.setFirstName("Sergey");

        mockMvc.perform(put("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getFilteredStudents() throws Exception {
        List<StudentDto> studentDtos = students.stream()
                .filter(student -> student.getGroupId() == 1).collect(Collectors.toList());

        mockMvc.perform(get("/student/filter")
                .param("groupId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(studentDtos)));
    }

    @Test
    public void getByIdShouldReturn404IfStudentWithIdNotFound() throws Exception {
        mockMvc.perform(get("/student/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("student with id 4 not found"));
    }

    @Test
    public void code400ShouldReturnIfValidationError() throws Exception {
        StudentDto student = students.get(0);
        student.setId(null);
        student.setFirstName("Iv");

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("size must be between 3 and 15"))
                .andExpect(status().isBadRequest());
    }
}
