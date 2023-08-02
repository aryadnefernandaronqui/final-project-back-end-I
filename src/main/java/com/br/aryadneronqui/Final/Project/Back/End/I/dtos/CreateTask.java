package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTask(

        UUID id,
        @NotBlank
        String email,
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        LocalDate date


) {
}
