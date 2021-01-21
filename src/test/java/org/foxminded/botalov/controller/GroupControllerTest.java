package org.foxminded.botalov.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.foxminded.botalov.model.group.GroupDto;
import org.foxminded.botalov.repository.specification.forgroup.GroupFilter;
import org.foxminded.botalov.service.GroupService;
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

@WebMvcTest(GroupController.class)
public class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService service;

    private final JsonMapper mapper = new JsonMapper();

    private final int groupId = 1;
    private final String groupName = "group1";

    @Test
    public void add() throws Exception {
        Mockito.when(service.save(new GroupDto(groupName))).thenReturn(new GroupDto(groupId, groupName));

        mockMvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new GroupDto(groupName))))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/group/" + groupId))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).save(new GroupDto(groupName));
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(groupId)).thenReturn(new GroupDto(groupId, groupName));

        mockMvc.perform(get("/group/" + groupId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(new GroupDto(groupId, groupName))))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findById(groupId);
    }

    @Test
    public void getAll() throws Exception {
        List<GroupDto> groups = List.of(new GroupDto(1), new GroupDto(2));

        Mockito.when(service.findAll()).thenReturn(groups);

        mockMvc.perform(get("/group"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(groups)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).findAll();
    }

    @Test
    public void update() throws Exception {
        mockMvc.perform(put("/group/" + groupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new GroupDto(groupId, groupName))))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).update(new GroupDto(groupId, groupName));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/group/" + groupId))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).delete(new GroupDto(groupId));
    }

    @Test
    public void getFilteredGroups() throws Exception {
        List<GroupDto> groups = List.of(new GroupDto(1), new GroupDto(2));

        Mockito.when(service.getFilteredGroups(any(GroupFilter.class)))
                .thenReturn(groups);

        mockMvc.perform(get("/group/filter")
                .param("groupId", "1")
                .param("groupName", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(groups)))
                .andReturn();

        Mockito.verify(service, Mockito.times(1)).getFilteredGroups(any(GroupFilter.class));
    }
}
