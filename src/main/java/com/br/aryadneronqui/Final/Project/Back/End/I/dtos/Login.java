package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
