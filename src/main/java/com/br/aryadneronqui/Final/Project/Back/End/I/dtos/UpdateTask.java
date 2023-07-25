package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;


import java.time.LocalDate;

public record UpdateTask(
        String userEmail,
        String title,
        String description,
        LocalDate date,
        boolean favorite,
        boolean archived
) {


}
