package com.br.aryadneronqui.Final.Project.Back.End.I.database;

import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;

import java.util.ArrayList;


public class Database {

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Task> tasks = new ArrayList<>();

    public static ArrayList<User> getUsers(){return Database.users;}
    public static ArrayList<Task> getTasks(){return Database.tasks;}

    public static void addUser(User user){users.add(user);}
    public static void addTask(Task task){tasks.add(task);}
    public static boolean userExist(String email){
        var userFiltered = users.stream().filter((user -> user.getEmail().equals(email))).findAny();
        return userFiltered.isPresent();
    }
    public static boolean taskExist(String title){
        var taskFiltered = tasks.stream().filter(task -> task.getTitle().equals(title)).findAny();
        return taskFiltered.isPresent();
    }
}
