package org.foxminded.botalov.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.teacher.TeacherDto;
import org.foxminded.botalov.repository.specification.forteacher.TeacherFilter;
import org.foxminded.botalov.service.TeacherService;
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

@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService service;

    private final JsonMapper mapper = new JsonMapper();

    private final int teacherId = 1;
    private final String firstName = "Ivan";
    private final String lastName = "Ivanov";
    private final int departmentId = 1;

    @Test
    public void add() throws Exception {
        TeacherDto teacher = new TeacherDto();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setDepartmentId(departmentId);

        Mockito.when(service.save(teacher)).thenReturn(new TeacherDto(teacherId));

        mockMvc.perform(post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(teacher)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/teacher/" + teacherId))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).save(teacher);
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(teacherId)).thenReturn(new TeacherDto(teacherId));

        mockMvc.perform(get("/teacher/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(new TeacherDto(teacherId))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(teacherId);
    }

    @Test
    public void getAll() throws Exception {
        List<TeacherDto> teachers = List.of(new TeacherDto(1), new TeacherDto(2));

        Mockito.when(service.findAll()).thenReturn(teachers);

        mockMvc.perform(get("/teacher"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(teachers)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void update() throws Exception {
        TeacherDto teacher = new TeacherDto();
        teacher.setId(teacherId);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setDepartmentId(departmentId);

        mockMvc.perform(put("/teacher/" + teacherId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(teacher)))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).update(teacher);
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/teacher/" + teacherId))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).delete(new TeacherDto(teacherId));
    }

    @Test
    public void getFilteredTeachers() throws Exception {
        List<TeacherDto> teachers = List.of(new TeacherDto(1), new TeacherDto(2));

        Mockito.when(service.getFilteredTeachers(any(TeacherFilter.class))).thenReturn(teachers);

        mockMvc.perform(get("/teacher/filter")
                .param("departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(teachers)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).getFilteredTeachers(any(TeacherFilter.class));
    }
}
