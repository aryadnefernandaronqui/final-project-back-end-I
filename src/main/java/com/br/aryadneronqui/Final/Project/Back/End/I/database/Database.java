package com.br.aryadneronqui.Final.Project.Back.End.I.database;

import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Database {

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Task> tasks = new ArrayList<>();

    public static ArrayList<User> getUsers(){return Database.users;}
    public static ArrayList<Task> getTasks(){return Database.tasks;}

    public static void addUser(User user){users.add(user);}
    public static void addTask(Task task){tasks.add(task);}

    public static User getUserByEmail(String email){
        var userFiltered = users.stream().filter((user -> user.getEmail().equals(email))).findAny();
        return userFiltered.orElse(null);
    }
    public static boolean taskExist(String title){
        var taskFiltered = tasks.stream().filter(task -> task.getTitle().equals(title)).findAny();
        return taskFiltered.isPresent();
    }
    public static Task getTaskById(UUID id){
        var taskById = tasks.stream().filter(task -> task.getId().equals(id)).findAny();
        return taskById.orElse(null);
    }
    public static void replaceModifiedTask(Task task){
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        tasks.add(task);
    }
    public static void removeTask(Task task){
        if(task.getId() == null){
            throw new RuntimeException("Invalid task.");
        }
        tasks.remove(task);
    }

    public static List<Task> getTasksUserLogged(User user){
        return tasks.stream().filter(task -> task.getUserId().equals(user.getEmail())).toList();
    }
}
