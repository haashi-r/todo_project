package com.interview.todo.service;

import com.interview.todo.enums.TodoStatus;
import com.interview.todo.mode.ProjectModel;
import com.interview.todo.mode.ToDoModel;
import com.interview.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private ToDoRepository toDoRepository;

    //task added
    public ToDoModel addTask( ToDoModel todo){
        todo.setCreatedDate(LocalDateTime.now());
        todo.setUpdatedDate(null);
        todo.setStatus(TodoStatus.NOT_STARTED);
        try{
           todo= toDoRepository.save(todo);
           return  todo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ToDoModel startUpdate(ToDoModel todo){
        todo.setStatus(TodoStatus.STARTED);
        todo.setUpdatedDate(LocalDateTime.now());
        try{
            todo= toDoRepository.save(todo);
            return  todo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //task move on PROGRESSING
    public ToDoModel progressUpdate(ToDoModel todo){
       todo.setStatus(TodoStatus.PROGRESSING);
       todo.setUpdatedDate(LocalDateTime.now());
        try{
            todo= toDoRepository.save(todo);
            return  todo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //task move on PENDING
    public ToDoModel pendingUpdate(ToDoModel todo){
        todo.setStatus(TodoStatus.PENDING);
        todo.setUpdatedDate(LocalDateTime.now());
        try{
            todo= toDoRepository.save(todo);
            return  todo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //task move on COMPLETED
    public ToDoModel completedUpdate(ToDoModel todo){
        todo.setStatus(TodoStatus.COMPLETED);
        todo.setUpdatedDate(LocalDateTime.now());
        try{
            todo= toDoRepository.save(todo);
            return  todo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //task move on NOT_COMPLETED
    public ToDoModel notCompletedUpdate(ToDoModel todo){
        todo.setStatus(TodoStatus.NOT_COMPLETED);
        todo.setUpdatedDate(LocalDateTime.now());
        try{
            todo= toDoRepository.save(todo);
            return  todo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Get all Task based on project
    public List<ToDoModel> getAllTskBasedOnProject(ProjectModel project){
        List<ToDoModel> todos= toDoRepository.findByProject(project);
        return  todos;
    }

}
