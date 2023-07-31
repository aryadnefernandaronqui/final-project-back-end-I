package com.br.aryadneronqui.Final.Project.Back.End.I.models;

import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    @Column(name = "user_name")
    private String name;
    private String email;
    private String password;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Task> tasks;
    @Column(name = "login_token")
    private String tokenLogin;


    public User(CreateUser newUser) {
        name = newUser.name();
        email = newUser.email();
        password = newUser.password();
        tasks = new ArrayList<>();
    }
    public String generateToken(){
        tokenLogin = UUID.randomUUID().toString();
        return tokenLogin;
    }

    public boolean isAuthenticated(String token){
        return tokenLogin != null && tokenLogin.equals(token);
    }
}
