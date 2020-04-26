package com.crud.task.controller;

import com.crud.task.domain.Task;
import com.crud.task.domain.TaskDto;
import com.crud.task.mapper.TaskMapper;
import com.crud.task.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "task", "task"));
        List<TaskDto> tasksDto = new ArrayList<>();
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        //When&Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "task", "task"));
        List<TaskDto> tasksDto = new ArrayList<>();
        tasksDto.add(new TaskDto(1L, "taskDto", "content"));
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        //When&Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("taskDto")))
                .andExpect(jsonPath("$[0].content", is("content")));
    }


    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        Task task = new Task(1L, "task", "task");
        TaskDto tasksDto = new TaskDto(1L, "taskDto", "content");
        when(dbService.getTask(ArgumentMatchers.any(Long.class))).thenReturn(java.util.Optional.of(task));
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(tasksDto);
        //When&Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("taskDto")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "task", "task");
        when(dbService.saveTask(task)).thenReturn(task);
        //When&Then
        mockMvc.perform(delete("/v1/task/deleteTask/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveTask() throws Exception {
        //Given
        Task task = new Task(1L, "task", "task");
        TaskDto taskDto = new TaskDto(1L, "taskDto", "content");
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When&Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "task", "task");
        Task updatedTask = new Task(1L, "taskupdted", "taskupdated");
        TaskDto updatedTaskDto = new TaskDto(1L, "taskupdated", "taskupdated");
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(updatedTask);
        when(dbService.saveTask(updatedTask)).thenReturn(updatedTask);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(updatedTaskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);
        //When&Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("taskupdated")))
                .andExpect(jsonPath("$.content", is("taskupdated")));
    }

}