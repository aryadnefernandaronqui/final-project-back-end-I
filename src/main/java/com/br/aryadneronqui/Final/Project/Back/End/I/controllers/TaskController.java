package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;

import com.br.aryadneronqui.Final.Project.Back.End.I.database.Database;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.ErrorData;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.OutputTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.UpdateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import com.br.aryadneronqui.Final.Project.Back.End.I.repositories.TaskRepository;
import com.br.aryadneronqui.Final.Project.Back.End.I.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{email}")
    public ResponseEntity getAll(
            @PathVariable("email") UUID userId,
            @RequestParam(required = false) String titleTask,
            @RequestParam(required = false) EStatus status,
            @RequestParam(required = false) boolean archived,
            @RequestHeader("AuthToken") String token) {



        var user = userRepository.findById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Account doesn't exist. Try a new e-mail."));
        }
        if(!user.is)

        var tasks = taskRepository.findAll();

        if (titleTask != null) {
            tasks = tasks.filter(t -> t.getTitle().contains(titleTask));
        }

        if (status != null){
            tasks = tasks.filter(t -> t.getStatus().equals(status));
        }

        if(archived){
            tasks.filter(t -> !t.isArchived());
        }

        return ResponseEntity.ok().body(tasks.map(OutputTask::new).toList());
    }

    @PostMapping
    public ResponseEntity createTask(@RequestHeader("AuthToken") String token, @RequestBody @Valid CreateTask newTask) {

        var user = Database.getUserByEmail(newTask.userId().toString());

        if (user != null && user.isAuthenticated(token)) {
            var task = new Task(newTask);

            if (Database.taskExist(newTask.title())) {
                return ResponseEntity.badRequest().body(new ErrorData("You already have a task with this title."));
            }

            Database.addTask(task);
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.badRequest().body(new ErrorData("Account doesn't exist. Try a new e-mail."));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity updateTask(@RequestHeader("AuthToken") String token, @PathVariable UUID id, @RequestBody UpdateTask modifiedTask) {

        var user = Database.getUserByEmail(modifiedTask.userEmail());
        var task = Database.getTaskById(id);

        if (user != null && user.isAuthenticated(token)) {
            if (task == null) {
                return ResponseEntity.badRequest().body(new ErrorData("Task doesn't exist. Try a new task."));
            }

            task.updateTaskInfo(modifiedTask);
            Database.replaceModifiedTask(task);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable UUID id) {

        var task = taskRepository.findById(id);

        if (task == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Task not found."));
        }

        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
