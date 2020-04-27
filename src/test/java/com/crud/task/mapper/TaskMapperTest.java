package com.crud.task.mapper;

import com.crud.task.domain.Task;
import com.crud.task.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "task", "make sth");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(1L, task.getId());
        assertEquals("task", task.getTitle());
        assertEquals("make sth", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "task", "make sth");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("task", taskDto.getTitle());
        assertEquals("make sth", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "task", "make sth"));
        //When
        List<TaskDto> list = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());
        assertEquals("task", list.get(0).getTitle());
        assertEquals("make sth", list.get(0).getContent());
    }

    @Test
    public void testMapToTaskDtoEmptyList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        //When
        List<TaskDto> list = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertTrue(list.isEmpty());
    }
}
