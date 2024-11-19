package com.interview.todo.repository;

import com.interview.todo.mode.ProjectModel;
import com.interview.todo.mode.ToDoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoModel , Integer> {
    List<ToDoModel> findByProject(ProjectModel project);
}
