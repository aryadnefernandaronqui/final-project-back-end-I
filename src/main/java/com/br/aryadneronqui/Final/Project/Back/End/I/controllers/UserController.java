package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;

import com.br.aryadneronqui.Final.Project.Back.End.I.database.Database;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateUser;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.ErrorData;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity getAll(){return ResponseEntity.ok().body(Database.getUsers());}


    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUser newUser){
        if(Database.userExist(newUser.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Account already exist. Try a different e-mail"));
        }

        var user = new User(newUser);

        Database.addUser(user);

    return ResponseEntity.ok().body(user);
    }

}
