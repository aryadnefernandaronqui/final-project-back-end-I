package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;

import com.br.aryadneronqui.Final.Project.Back.End.I.database.Database;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.ErrorData;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public ResponseEntity getAll(){return ResponseEntity.ok().body(Database.getTasks());}

    @PostMapping
    public ResponseEntity createTask(@RequestBody  @Valid CreateTask newTask){
        if(Database.taskExist(newTask.title())){
            return ResponseEntity.badRequest().body(new ErrorData("You already have a task with this title."));
        }
        if(Database.userExist(newTask.userEmail())){
            var task = new Task(newTask);

            Database.addTask(task);
            return ResponseEntity.ok().body(task);

        } else {
            return ResponseEntity.badRequest().body(new ErrorData("Account doesn't exist. Try a new e-mail."));
        }

    }

}
