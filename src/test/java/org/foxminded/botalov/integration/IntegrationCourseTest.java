package org.foxminded.botalov.integration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.course.CourseDto;
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
public class IntegrationCourseTest {

    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    private final List<CourseDto> courses = List.of(
            new CourseDto(1, "course1", 1, "department1"),
            new CourseDto(2, "course2", 1, "department1"),
            new CourseDto(3, "course3", 2, "department2")
    );

    @Test
    public void findById() throws Exception {

        mockMvc.perform(get("/course/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(courses.get(0)))).andReturn();
    }

    @Test
    public void getAllCourses() throws Exception {

        mockMvc.perform(get("/course").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(courses)));
    }

    @Test
    public void addCourse() throws Exception {
        CourseDto courseDto = courses.get(0);
        courseDto.setId(null);
        courseDto.setName("course4");

        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(courseDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/course/4"));
    }

    @Test
    public void deleteCourse() throws Exception {
        mockMvc.perform(delete("/course/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateCourse() throws Exception {
        CourseDto courseDto = courses.get(0);
        courseDto.setName("course4");

        mockMvc.perform(put("/course/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(courseDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getFilteredCourses() throws Exception {
        List<CourseDto> courseDtos = courses.stream()
                .filter(course -> course.getDepartmentId() == 1).collect(Collectors.toList());

        mockMvc.perform(get("/course/filter")
                .param("departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(courseDtos)));
    }

    @Test
    public void getByIdShouldReturn404IfCourseWithIdNotFound() throws Exception {
        mockMvc.perform(get("/course/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("course with id 4 not found"));
    }

    @Test
    public void code400ShouldReturnIfValidationError() throws Exception {
        CourseDto course = courses.get(0);
        course.setId(null);
        course.setName("co");

        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course)))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("size must be between 3 and 30"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void code400ShouldReturnWhenDatabaseConstraintIsViolated() throws Exception {
        CourseDto course = courses.get(0);
        course.setId(null);

        mockMvc.perform(post("/course")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(course)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("course with the specified name already exists"));
    }
}
