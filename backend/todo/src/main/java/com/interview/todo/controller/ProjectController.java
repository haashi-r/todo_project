package com.interview.todo.controller;

import com.interview.todo.dto.request.ProjectTitleEditDTO;
import com.interview.todo.dto.request.RequestProjectDTO;
import com.interview.todo.mode.ProjectModel;
import com.interview.todo.repository.ProjectRepository;
import com.interview.todo.service.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectServices projectServices;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(path = "/save", consumes = {"application/json"})
    public ResponseEntity<Map<String,Object>> createProject(@RequestBody RequestProjectDTO projectReq){
        Map<String,Object> response = new HashMap<>();
        Map<String,String> message = new HashMap<>();
        if(projectReq == null){
            response.put("success",false);
            message.put("message","Please enter project title");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ProjectModel projectModel = new ProjectModel();
        projectModel.setTitle(projectReq.getTitle());

        projectModel = projectServices.projectCreation(projectModel);

        if(projectModel.getProjectId() <= 0){
            response.put("success",false);
            message.put("message","Sorry project not created..");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("success",true);
        response.put("project",projectModel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/get/all" , produces =  {"application/json"})
    public ResponseEntity<Map<String,Object>> getAllProject() {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        List<ProjectModel> projects =projectServices.viewAllProject();
        if(projects.isEmpty()){
            response.put("success",false);
            message.put("message","No projects existing..");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        projects.stream().forEach( project -> project.setTodos(null));
        response.put("success",true);
        response.put("projects",projects);
        return  ResponseEntity.ok(response);
    }

    @PutMapping(path = "/edit/title" , produces =  {"application/json"})
    public ResponseEntity<Map<String,Object>> getAllProject(@RequestBody ProjectTitleEditDTO projectTitleEditDTO) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        ProjectModel project= projectRepository.findById(projectTitleEditDTO.getProjectId()).orElse(null);
        if(project == null){
            response.put("success",false);
            message.put("message","Project not found");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        project.setTitle(projectTitleEditDTO.getTitle());

        project = projectServices.editProjectTitle(project);
        if(!project.getTitle().equals(projectTitleEditDTO.getTitle())){
            response.put("success",false);
            message.put("message","Project title not changed");
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.put("success",true);
        response.put("project",project);
        return ResponseEntity.ok(response);

    }

}
