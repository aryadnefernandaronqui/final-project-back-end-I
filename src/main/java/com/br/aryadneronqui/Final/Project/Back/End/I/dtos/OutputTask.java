package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;
import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;

import java.time.LocalDate;
import java.util.UUID;

public record OutputTask(
        String title,
        String description,
        LocalDate date,
        EStatus status,
        UUID id,
        boolean favorite,
        boolean archived
        ) {
    public OutputTask(Task task) {
        this(task.getTitle(),
                task.getDescription(),
                task.getDate(),
                task.getStatus(),
                task.getId(),
                task.isFavorite(),
                task.isArchived());
    }
}
