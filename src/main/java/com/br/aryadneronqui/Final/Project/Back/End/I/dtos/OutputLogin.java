package com.br.aryadneronqui.Final.Project.Back.End.I.dtos;

import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;

public record OutputLogin(
        String token,
        String email,
        String name
) {
    public OutputLogin(String token, User user) {
        this(
                token,
                user.getEmail(),
                user.getName()
        );
    }
}
