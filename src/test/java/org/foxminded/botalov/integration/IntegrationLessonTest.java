package org.foxminded.botalov.integration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.foxminded.botalov.model.lesson.LessonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("/test_data.sql")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationLessonTest {

    @Autowired
    private MockMvc mockMvc;

    private JsonMapper mapper;

    private List<LessonDto> lessons;

    @BeforeEach
    public void initialize() {
        mapper = new JsonMapper();
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        lessons = List.of(
                new LessonDto(
                        1,
                        Instant.parse("2099-12-10T09:00:00Z"),
                        Instant.parse("2099-12-10T10:30:00Z"),
                        1, "course1",
                        1, "group1",
                        1, "Ivan", "Ivanov",
                        101),
                new LessonDto(
                        2,
                        Instant.parse("2099-12-10T10:30:00Z"),
                        Instant.parse("2099-12-10T12:00:00Z"),
                        2, "course2",
                        2, "group2",
                        2, "Petr", "Petrov",
                        102),
                new LessonDto(
                        3,
                        Instant.parse("2099-12-10T12:00:00Z"),
                        Instant.parse("2099-12-10T13:30:00Z"),
                        3, "course3",
                        3, "group3",
                        3, "Andrey", "Andreev",
                        103),
                new LessonDto(
                        4,
                        Instant.parse("2099-12-10T13:30:00Z"),
                        Instant.parse("2099-12-10T15:00:00Z"),
                        1, "course1",
                        1, "group1",
                        1, "Ivan", "Ivanov",
                        104)
        );
    }

    @Test
    public void findById() throws Exception {

        mockMvc.perform(get("/lesson/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(lessons.get(0)))).andReturn();
    }

    @Test
    public void getAllLessons() throws Exception {

        mockMvc.perform(get("/lesson").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(lessons)));
    }

    @Test
    public void addLesson() throws Exception {
        LessonDto lessonDto = lessons.get(0);
        lessonDto.setId(null);
        lessonDto.setTeacherId(3);

        mockMvc.perform(post("/lesson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lessonDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/lesson/5"));
    }

    @Test
    public void deleteLesson() throws Exception {
        mockMvc.perform(delete("/lesson/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateLesson() throws Exception {
        LessonDto lessonDto = lessons.get(0);
        lessonDto.setClassroom(105);

        mockMvc.perform(put("/lesson/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lessonDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getFilteredLessons() throws Exception {
        List<LessonDto> lessonDtos = lessons.stream()
                .filter(course -> course.getTeacherId() == 1).collect(Collectors.toList());

        mockMvc.perform(get("/lesson/filter")
                .param("teacherId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(lessonDtos)));
    }

    @Test
    public void getByIdShouldReturn404IfLessonWithIdNotFound() throws Exception {
        mockMvc.perform(get("/lesson/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("lesson with id 5 not found"));
    }

    @Test
    public void code400ShouldReturnIfValidationError() throws Exception {
        LessonDto lesson = lessons.get(0);
        lesson.setId(null);
        lesson.setStart(Instant.parse("2010-12-10T09:00:00Z"));

        mockMvc.perform(post("/lesson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lesson)))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("lesson duration must be 90 minutes and end time must be later than start time"))
                .andExpect(status().isBadRequest());
    }
}
