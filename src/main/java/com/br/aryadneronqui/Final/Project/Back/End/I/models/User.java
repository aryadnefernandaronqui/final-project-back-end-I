package com.br.aryadneronqui.Final.Project.Back.End.I.models;

import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateUser;
import lombok.Getter;

import java.util.ArrayList;


@Getter

public class User {

    private String name;
    private String email;
    private String password;
    private ArrayList<Task> tasks;
    private String tokenLogin;

    public User(CreateUser newUser) {
        name = newUser.name();
        email = newUser.email();
        password = newUser.password();
        tasks = new ArrayList<>();
    }


}
