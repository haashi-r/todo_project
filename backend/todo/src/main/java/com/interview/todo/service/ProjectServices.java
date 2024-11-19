package com.interview.todo.service;

import com.interview.todo.mode.ProjectModel;
import com.interview.todo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServices {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectModel projectCreation(ProjectModel projectModel){
        projectModel.setCreatedDate(LocalDateTime.now());
        try {
            projectRepository.save(projectModel);
            return projectModel;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProjectModel> viewAllProject(){
        List<ProjectModel> projects = projectRepository.findAll();
        return  projects;
    }

    public ProjectModel editProjectTitle(ProjectModel projectModel){
        ProjectModel project= projectRepository.save(projectModel);
        return project;
    }

}
