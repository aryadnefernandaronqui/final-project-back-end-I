package com.br.aryadneronqui.Final.Project.Back.End.I.repositories;

import com.br.aryadneronqui.Final.Project.Back.End.I.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
     User getReferenceByEmail(String email);


}
