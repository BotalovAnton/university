package org.foxminded.botalov.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.course.CourseDto;
import org.foxminded.botalov.service.CourseService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService service;

    private final JsonMapper mapper = new JsonMapper();

    private final int courseId = 1;
    private final String courseName = "course1";
    private final int departmentId = 1;


    @Test
    public void add() throws Exception {
        CourseDto course = new CourseDto();
        course.setName(courseName);
        course.setDepartmentId(departmentId);

        Mockito.when(service.save(course)).thenReturn(new CourseDto(courseId));

        mockMvc.perform(post("/course/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/course/" + courseId))
                .andReturn();



        Mockito.verify(service, Mockito.times(1)).save(course);
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(courseId)).thenReturn(new CourseDto(courseId));

        mockMvc.perform(get("/course/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(new CourseDto(courseId))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(courseId);
    }

    @Test
    public void getAll() throws Exception {

        List<CourseDto> courses = List.of(new CourseDto(1), new CourseDto(2));

        Mockito.when(service.findAll()).thenReturn(courses);

        mockMvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(courses)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void update() throws Exception {
        CourseDto course = new CourseDto();
        course.setId(courseId);
        course.setName(courseName);
        course.setDepartmentId(departmentId);

        mockMvc.perform(put("/course/1")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(course)))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).update(course);
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/course/1"))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).delete(new CourseDto(courseId));
    }

    @Test
    public void findByFilterTest() throws Exception {
        Mockito.when(service.getFilteredCourses(any())).thenReturn(Collections.singletonList(new CourseDto(courseId)));

        mockMvc.perform(get("/course/filter")
                .param("courseId", "1")
                .param("courseName", "")
                .param("departmentId", "")
                .param("departmentName", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(Collections.singletonList(new CourseDto(courseId)))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).getFilteredCourses(any());
    }
}
