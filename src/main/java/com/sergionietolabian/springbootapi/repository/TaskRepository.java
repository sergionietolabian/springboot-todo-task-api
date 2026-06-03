package com.sergionietolabian.springbootapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergionietolabian.springbootapi.entity.Task;
import com.sergionietolabian.springbootapi.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByUserUsername(String username);
    List<Task> findByStatusAndTitleContaining(TaskStatus status, String title);
}