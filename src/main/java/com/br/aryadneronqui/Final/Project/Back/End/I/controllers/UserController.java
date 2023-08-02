package com.br.aryadneronqui.Final.Project.Back.End.I.controllers;

import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateUser;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.ErrorData;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.Login;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import com.br.aryadneronqui.Final.Project.Back.End.I.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAll(){
        var getAllUsers = userRepository.findAll();
        return ResponseEntity.ok().body(getAllUsers);}



    @PostMapping
    @Transactional
    public ResponseEntity createUser(@RequestBody @Valid CreateUser newUser){
        if(userRepository.existsByEmail(newUser.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Account already exist. Try a different e-mail"));
        }

        var user = new User(newUser);
    return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity login(@RequestBody @Valid Login newLogin){

            var userLogged = userRepository.getReferenceByEmail(newLogin.email());
            if(userLogged == null){
                return ResponseEntity.badRequest().body(new ErrorData("User not found."));
            }
            if(!userLogged.getPassword().equals(newLogin.password())){
                return ResponseEntity.badRequest().body(new ErrorData("Password doesn't match. Try again."));

        }
            var token = userLogged.generateToken();
        return ResponseEntity.ok().body(token);
    }

}
