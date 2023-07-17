package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;

import com.br.aryadneronqui.Final.Project.Back.End.I.database.Database;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.ErrorData;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.OutputTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.UpdateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping("/{email}")
    public ResponseEntity getAll(
            @PathVariable("email") String userEmail,
            @RequestParam(required = false) String titleTask,
                                 @RequestParam(required = false) EStatus status,
                                 @RequestParam(required = false) boolean archived,

                                 @RequestHeader("AuthToken") String token) {

        var user = Database.getUserByEmail(userEmail);

        if (!user.isAuthenticated(token)) {
            return ResponseEntity.badRequest().body(new ErrorData("Account doesn't exist. Try a new e-mail."));
        }

        var tasks = Database.getTasksUserLogged(user).stream();

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

        var user = Database.getUserByEmail(newTask.userEmail());

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
        var task = Database.getTaskById(id);

        if (task == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Task not found."));
        }

        Database.removeTask(task);

        return ResponseEntity.noContent().build();
    }


}
