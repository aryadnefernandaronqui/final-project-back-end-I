package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;

import com.br.aryadneronqui.Final.Project.Back.End.I.database.Database;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.*;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity getAll(){return ResponseEntity.ok().body(Database.getUsers());}


    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUser newUser){
        var userExist = Database.getUserByEmail(newUser.email());

        if(userExist != null && userExist.equals(newUser.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Account already exist. Try a different e-mail"));
        }

        var user = new User(newUser);

        Database.addUser(user);

    return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Login newLogin){

            var userLogged = Database.getUserByEmail(newLogin.email());
            if(userLogged == null){
                return ResponseEntity.badRequest().body(new ErrorData("User not found."));
            }
            if(!userLogged.getPassword().equals(newLogin.password())){
                return ResponseEntity.badRequest().body(new ErrorData("Password doesn't match. Try again."));

        }
        return ResponseEntity.ok().body(userLogged.generateToken());
    }

}
