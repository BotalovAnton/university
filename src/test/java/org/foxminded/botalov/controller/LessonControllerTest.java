package org.foxminded.botalov.controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.repository.specification.forlesson.LessonFilter;
import org.foxminded.botalov.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService service;

    private JsonMapper mapper;

    private final int lessonId = 1;
    private final Instant start = Instant.parse("2921-01-06T09:00:00Z");
    private final Instant end = Instant.parse("2921-01-06T10:30:00Z");
    private final int courseId = 1;
    private final int groupId = 1;
    private final int teacherId = 1;
    private final int classroom = 101;

    @BeforeEach
    public void initialize() {
        mapper = new JsonMapper();
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void add() throws Exception {
        LessonDto lesson = new LessonDto();
        lesson.setStart(start);
        lesson.setEnd(end);
        lesson.setCourseId(courseId);
        lesson.setGroupId(groupId);
        lesson.setTeacherId(teacherId);
        lesson.setClassroom(classroom);

        Mockito.when(service.save(lesson)).thenReturn(new LessonDto(lessonId));

        System.out.println(mapper.writeValueAsString(start));

        mockMvc.perform(post("/lesson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lesson)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/lesson/" + lessonId))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).save(lesson);
    }

    @Test
    public void findById() throws Exception {
        LessonDto lesson = new LessonDto();
        lesson.setId(lessonId);
        lesson.setStart(start);
        lesson.setEnd(end);
        lesson.setCourseId(courseId);
        lesson.setGroupId(groupId);
        lesson.setTeacherId(teacherId);
        lesson.setClassroom(classroom);

        Mockito.when(service.findById(lessonId)).thenReturn(lesson);

        mockMvc.perform(get("/lesson/" + lessonId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(lesson)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(lessonId);
    }

    @Test
    public void getAll() throws Exception {
        List<LessonDto> lessons = List.of(new LessonDto(1), new LessonDto(2));

        Mockito.when(service.findAll()).thenReturn(lessons);

        mockMvc.perform(get("/lesson"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(lessons)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void update() throws Exception {
        LessonDto lesson = new LessonDto();
        lesson.setId(lessonId);
        lesson.setStart(start);
        lesson.setEnd(end);
        lesson.setCourseId(courseId);
        lesson.setGroupId(groupId);
        lesson.setTeacherId(teacherId);
        lesson.setClassroom(classroom);

        mockMvc.perform(put("/lesson/" + lessonId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lesson)))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).update(lesson);
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/lesson/" + lessonId))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).delete(new LessonDto(lessonId));
    }

    @Test
    public void getFilteredLessons() throws Exception {
        List<LessonDto> lessons = List.of(new LessonDto(1), new LessonDto(2));

        Mockito.when(service.getFilteredLessons(any(LessonFilter.class))).thenReturn(lessons);

        mockMvc.perform(get("/lesson/filter")
                .param("lessonId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(lessons)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).getFilteredLessons(any(LessonFilter.class));
    }
}
