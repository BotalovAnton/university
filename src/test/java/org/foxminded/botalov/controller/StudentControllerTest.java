package org.foxminded.botalov.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.student.StudentDto;
import org.foxminded.botalov.repository.specification.forstudent.StudentFilter;
import org.foxminded.botalov.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    private final JsonMapper mapper = new JsonMapper();

    private final int studentId = 1;
    private final String studentFirstName = "Ivan";
    private final String studentLastName = "Ivanov";
    private final int groupId = 1;

    @Test
    public void getAll() throws Exception {
        List<StudentDto> students = List.of(new StudentDto(1), new StudentDto(2));

        Mockito.when(service.findAll()).thenReturn(students);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(students)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(studentId)).thenReturn(new StudentDto(studentId));

        mockMvc.perform(get("/student/" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(new StudentDto(studentId))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(studentId);
    }

    @Test
    public void add() throws Exception {
        StudentDto student = new StudentDto();
        student.setFirstName(studentFirstName);
        student.setLastName(studentLastName);
        student.setGroupId(groupId);

        Mockito.when(service.save(student)).thenReturn(new StudentDto(studentId));

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/student/" + studentId))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).save(student);
    }

    @Test
    public void update() throws Exception {
        StudentDto student = new StudentDto();
        student.setId(studentId);
        student.setFirstName(studentFirstName);
        student.setLastName(studentLastName);
        student.setGroupId(groupId);

        mockMvc.perform(put("/student/" + studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).update(student);
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/student/" + studentId))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).delete(new StudentDto(studentId));
    }

    @Test
    public void getFilteredStudents() throws Exception {
        List<StudentDto> students = List.of(new StudentDto(1), new StudentDto(2));

        Mockito.when(service.getFilteredStudents(any(StudentFilter.class)))
                .thenReturn(students);

        mockMvc.perform(get("/student/filter")
                .param("studentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(students)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).getFilteredStudents(any(StudentFilter.class));
    }
}
