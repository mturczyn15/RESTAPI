package com.crud.task.service;

import com.crud.task.domain.Task;
import com.crud.task.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void testGetAllTasks() {
        //Given
        List<Task> list = new ArrayList<>();
        list.add(new Task(1L, "task", "make"));
        when(repository.findAll()).thenReturn(list);
        //When
        List<Task> retrievedList = dbService.getAllTasks();
        //Then
        assertEquals(1, retrievedList.size());
    }

    @Test
    public void testSaveTask() {
        //Given

        Task task = new Task(1L, "task", "make");
        when(repository.save(task)).thenReturn(task);
        //When
        Task retrievedTask = dbService.saveTask(task);
        //Then
        assertEquals(1L, retrievedTask.getId());
    }

    @Test
    public void testDeleteTask() {
        //Given
        Task task = new Task(1L, "task", "make");
        when(repository.save(task)).thenReturn(task);
        dbService.saveTask(task);
        //When
        dbService.deleteTask(1L);
        //Then
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void testGetTask() {
        //Given
        Task task = new Task(1L, "task", "make");
        Optional<Task> optional = Optional.of(task);
        when(repository.save(task)).thenReturn(task);
        when(repository.findById(1L)).thenReturn(optional);
        repository.save(task);
        //When
        Optional<Task> retrievedOptional = dbService.getTask(1L);
        //Then
        assertTrue(retrievedOptional.isPresent());
    }
}
