package org.foxminded.botalov.integration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.group.GroupDto;
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
public class IntegrationGroupTest {

    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    private final List<GroupDto> groups = List.of(
            new GroupDto(1, "group1"),
            new GroupDto(2, "group2"),
            new GroupDto(3, "group3")
    );

    @Test
    public void addGroup() throws Exception {
        GroupDto group = new GroupDto("group4");

        mockMvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(group)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/group/4"));
    }

    @Test
    public void getAllGroups() throws Exception {
        mockMvc.perform(get("/group").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(groups)));
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/group/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(groups.get(0))));
    }

    @Test
    public void updateCourse() throws Exception {
        GroupDto group = new GroupDto(1, "group4");

        mockMvc.perform(put("/group/1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(group)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCourse() throws Exception {
        mockMvc.perform(delete("/group/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getByIdShouldReturn404IfGroupWithIdNotFound() throws Exception {
        mockMvc.perform(get("/group/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("group with id 4 not found"));
    }

    @Test
    public void getFilteredGroups() throws Exception {
        mockMvc.perform(get("/group/filter").accept(MediaType.APPLICATION_JSON)
                .param("groupName", "group1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(Collections.singletonList(groups.get(0)))));
    }

    @Test
    public void code400ShouldReturnIfValidationError() throws Exception {
        GroupDto group = groups.get(0);
        group.setId(null);
        group.setName("gr");

        mockMvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(group)))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("size must be between 3 and 10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void code400ShouldReturnWhenDatabaseConstraintIsViolated() throws Exception {
        GroupDto group = groups.get(0);
        group.setId(null);

        mockMvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(group)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("group with the specified name already exists"));
    }
}
