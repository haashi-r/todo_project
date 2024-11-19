package com.interview.todo.controller;


import com.interview.todo.dto.request.ProjectIdReq;
import com.interview.todo.dto.request.TaskCreationDTO;
import com.interview.todo.dto.request.TaskStatusUpdateReqDTO;
import com.interview.todo.enums.TodoStatus;
import com.interview.todo.mode.ProjectModel;
import com.interview.todo.mode.ToDoModel;
import com.interview.todo.repository.ProjectRepository;

import com.interview.todo.repository.ToDoRepository;
import com.interview.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TasKController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private ToDoRepository toDoRepository;

    @PostMapping(path = "/save", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> createProject(@RequestBody TaskCreationDTO taskAddReq) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if(taskAddReq == null){
            response.put("success",false);
            message.put("message","Task Description not added");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ProjectModel project =projectRepository.findById(taskAddReq.getProjectId()).orElse(null);

        if(project == null){
            response.put("success",false);
            message.put("message","Project not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ToDoModel todo = new ToDoModel();
        todo.setDescription(taskAddReq.getDescription());
        todo.setProject(project);

        todo =todoService.addTask(todo);
        if (todo.getStatus() != TodoStatus.NOT_STARTED){
            response.put("success",false);
            message.put("message","Task not created");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("task",todo);
        return  ResponseEntity.ok(response);
    }


    @PostMapping(path = "/get/task/id", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> taskGetFindById(@RequestBody TaskStatusUpdateReqDTO taskid) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        ToDoModel todo = toDoRepository.findById(taskid.getTaskId()).orElse(null);
        if(todo == null){
            response.put("success",false);
            message.put("message","Task not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("success",true);
        response.put("todo",todo);
        return  ResponseEntity.ok(response);
    }

    @PostMapping(path = "/get/project/id", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> taskGetFindByProjectId(@RequestBody ProjectIdReq projectIdReq) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        ProjectModel projectModel = projectRepository.findById(projectIdReq.getProjectId()).orElse(null);
        if(projectModel == null){
            response.put("success",false);
            message.put("message","Project not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<ToDoModel> todos = todoService.getAllTskBasedOnProject(projectModel);
        if (todos.isEmpty()){
            response.put("success",false);
            message.put("message","No task in this project");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("success",true);
        response.put("todos",todos);
        return  ResponseEntity.ok(response);
    }


    @PutMapping(path = "/status/start", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> createProject(@RequestBody TaskStatusUpdateReqDTO taskStatusUpdateReqDTO) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if(taskStatusUpdateReqDTO == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ToDoModel toDo = toDoRepository.findById(taskStatusUpdateReqDTO.getTaskId()).orElse(null);
        if(toDo == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        toDo = todoService.startUpdate(toDo);

        if (toDo.getStatus() != TodoStatus.STARTED){
            response.put("success",false);
            message.put("message","Task id not started");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("task",toDo);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/status/progressing", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> progressingProject(@RequestBody TaskStatusUpdateReqDTO taskStatusUpdateReqDTO) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if(taskStatusUpdateReqDTO == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ToDoModel toDo = toDoRepository.findById(taskStatusUpdateReqDTO.getTaskId()).orElse(null);
        if(toDo == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        toDo = todoService.progressUpdate(toDo);

        if (toDo.getStatus() != TodoStatus.PROGRESSING){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("task",toDo);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/status/pending", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> pendingProject(@RequestBody TaskStatusUpdateReqDTO taskStatusUpdateReqDTO) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if(taskStatusUpdateReqDTO == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ToDoModel toDo = toDoRepository.findById(taskStatusUpdateReqDTO.getTaskId()).orElse(null);
        if(toDo == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        toDo = todoService.pendingUpdate(toDo);

        if (toDo.getStatus() != TodoStatus.PENDING){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("task",toDo);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/status/completed", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> completedProject(@RequestBody TaskStatusUpdateReqDTO taskStatusUpdateReqDTO) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if(taskStatusUpdateReqDTO == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ToDoModel toDo = toDoRepository.findById(taskStatusUpdateReqDTO.getTaskId()).orElse(null);
        if(toDo == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        toDo = todoService.completedUpdate(toDo);

        //if you want delete after task completed
//        toDoRepository.delete(toDo);

        if (toDo.getStatus() != TodoStatus.COMPLETED){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("task",toDo);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/status/not-completed", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> notCompletedProject(@RequestBody TaskStatusUpdateReqDTO taskStatusUpdateReqDTO) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if(taskStatusUpdateReqDTO == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ToDoModel toDo = toDoRepository.findById(taskStatusUpdateReqDTO.getTaskId()).orElse(null);
        if(toDo == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        toDo = todoService.notCompletedUpdate(toDo);

        if (toDo.getStatus() != TodoStatus.NOT_COMPLETED){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("task",toDo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<Map<String,Object>> deleteTaskById(@RequestBody TaskStatusUpdateReqDTO taskStatusUpdateReqDTO) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        ToDoModel toDo = toDoRepository.findById(taskStatusUpdateReqDTO.getTaskId()).orElse(null);

        if(toDo == null){
            response.put("success",false);
            message.put("message","Task id not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        toDoRepository.delete(toDo);
        response.put("success",true);
        response.put("message","deleted task : "+ toDo.getDescription());
        return ResponseEntity.ok(response);

    }


}
