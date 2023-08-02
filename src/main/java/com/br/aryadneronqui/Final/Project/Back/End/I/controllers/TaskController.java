package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;


import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.ErrorData;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.OutputTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.UpdateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import com.br.aryadneronqui.Final.Project.Back.End.I.repositories.TaskRepository;
import com.br.aryadneronqui.Final.Project.Back.End.I.repositories.UserRepository;
import com.br.aryadneronqui.Final.Project.Back.End.I.repositories.specifications.TaskSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity getAll(
            @PathVariable UUID userId,
            @RequestParam(required = false) String titleTask,
            @RequestParam(required = false) EStatus status,
            @RequestParam(required = false) boolean archived,
            @RequestHeader("AuthToken") String token) {


        var optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("Account doesn't exist. Try a new e-mail."));
        }
        if (!optionalUser.get().isAuthenticated(token)) {
            return ResponseEntity.badRequest().body(new ErrorData("Invalid token."));
        }

        var specification = TaskSpecifications.filterByTitleAndStatusAndArchived(titleTask, status, archived);

        var task = taskRepository.findAll(specification).stream().map(OutputTask::new).toList();


        return ResponseEntity.ok(task);

    }

    @PostMapping
    @Transactional
    public ResponseEntity createTask(@RequestHeader("AuthToken") String token, @RequestBody @Valid CreateTask newTask) {

        var user = userRepository.getReferenceByEmail(newTask.email());
        var optionalTask = taskRepository.findAll();


        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Account doesn't exist. Try a new e-mail."));
        }

        if (user.isAuthenticated(token)) {
            if (optionalTask.contains(newTask.title())) {
                return ResponseEntity.badRequest().body(new ErrorData("You already have a task with this title."));
            }

        }

        var task = new Task(newTask);
        taskRepository.save(task);
        return ResponseEntity.ok().body(task);

    }

    @PutMapping("/{id}/{userId}")
    @Transactional
    public ResponseEntity updateTask(@RequestHeader("AuthToken") String token,
                                     @PathVariable UUID id,
                                     @PathVariable UUID userId,
                                     @RequestBody UpdateTask modifiedTask) {

        var optionalUser = userRepository.findById(userId);
        var optionalTask = taskRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("User not found."));
        }
        if (optionalUser.get().isAuthenticated(token)) {
            return ResponseEntity.badRequest().body(new ErrorData("Token not valid"));
        }
        if (optionalTask.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("Task doesn't exist. Try a new task."));
        }

        var task = optionalTask.get();

        task.updateTaskInfo(modifiedTask);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{id}/{userId}")
    @Transactional
    public ResponseEntity deleteTask(@PathVariable UUID id, @PathVariable UUID userId) {

        var user = userRepository.findById(userId);
        var task = taskRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("User not found."));
        }
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("Task not found."));
        }

        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
