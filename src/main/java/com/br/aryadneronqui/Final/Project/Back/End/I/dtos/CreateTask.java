package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTask(
        @NotBlank
        String userEmail,
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        LocalDate date
) {
}
