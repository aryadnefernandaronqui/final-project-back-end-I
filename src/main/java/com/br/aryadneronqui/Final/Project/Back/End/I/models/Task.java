package com.br.aryadneronqui.Final.Project.Back.End.I.models;

import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.UpdateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import lombok.Getter;


import java.time.LocalDate;
import java.util.UUID;

@Getter

public class Task {
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private boolean favorite;
    private boolean archived;
    private String userId;
    private EStatus status;

    public Task(CreateTask newTask){
        id = UUID.randomUUID();
        title = newTask.title();
        description = newTask.description();
        date = newTask.date();
        userId = newTask.userEmail();
        favorite = false;
        archived = false;
        status = EStatus.MISSING;
    }

    public void updateTaskInfo(UpdateTask modifiedTaskInfo){
        if(modifiedTaskInfo.title() != null){
            title = modifiedTaskInfo.title();
        }
        if(modifiedTaskInfo.description() != null){
            description = modifiedTaskInfo.description();
        }
        if(modifiedTaskInfo.date() != null){
            date = modifiedTaskInfo.date();
        }
    }

}
