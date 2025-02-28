package com.tasks.tma.repository;

import com.tasks.tma.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByWorkspaceId(UUID workspaceId);
}
