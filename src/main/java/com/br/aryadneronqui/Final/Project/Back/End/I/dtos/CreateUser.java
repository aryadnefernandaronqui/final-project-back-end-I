package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CreateUser(
        @NotBlank
        @Length(min = 3, max = 30)
        String name,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Length(min = 6, max = 20)
        String password
) {
}
