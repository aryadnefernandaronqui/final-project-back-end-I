package com.br.aryadneronqui.Final.Project.Back.End.I.models;

import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.CreateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.dtos.UpdateTask;
import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "task_id")
    private UUID id;
    @Column(name = "task_name")
    private String title;
    private String description;
    private LocalDate date;
    private boolean favorite;
    private boolean archived;
    @Column(name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private EStatus status;

    public Task(CreateTask newTask){

        title = newTask.title();
        description = newTask.description();
        date = newTask.date();
        userId = newTask.userId();
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
