package com.sergionietolabian.springbootapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sergionietolabian.springbootapi.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}