package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateTask(
        String userEmail,
        String title,
        String description,
        LocalDate date
) {


}
