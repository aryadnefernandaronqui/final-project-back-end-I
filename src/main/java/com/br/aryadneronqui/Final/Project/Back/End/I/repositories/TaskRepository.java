package com.br.aryadneronqui.Final.Project.Back.End.I.repositories;

import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}
