package com.tasks.tma.service;

import com.tasks.tma.dtos.PaginatedResponse;
import com.tasks.tma.dtos.TaskRequestDTO;
import com.tasks.tma.dtos.TaskResponseDTO;
import com.tasks.tma.dtos.TaskStatusUpdateDTO;
import com.tasks.tma.enums.TaskStatus;
import com.tasks.tma.exception.ResourceNotFoundException;
import com.tasks.tma.mappers.TaskMapper;
import com.tasks.tma.models.Task;
import com.tasks.tma.models.Workspace;
import com.tasks.tma.repository.TaskRepository;
import com.tasks.tma.utilitas.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final Helper helper;

    public PaginatedResponse<TaskResponseDTO> getAllTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findAll(pageable);

        System.out.println(taskRepository.findAll().size());
        List<TaskResponseDTO> taskResponses = mapTasksToDTOs(taskPage.getContent());

        return buildPaginatedResponse(taskResponses, taskPage);
    }
    public TaskResponseDTO getTaskById(UUID taskId) {
         Task task= helper.findTaskById(taskId);
         return taskMapper.taskToTaskResponseDTO(task);
    }
    public List<TaskResponseDTO> getTasksByWorkspace(UUID workspaceId) {
        Workspace workspace = helper.findWorkspaceById(workspaceId);
        return taskRepository.findAllByWorkspaceId(workspaceId).stream()
               .map(taskMapper::taskToTaskResponseDTO)
               .collect(Collectors.toList());
    }

   public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
       Workspace workspace = helper.findWorkspaceById(taskRequestDTO.getWorkspaceId());

         Task task = createTaskObject(taskRequestDTO,workspace);

         taskRepository.save(task);

         return taskMapper.taskToTaskResponseDTO(task);

   }
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO taskRequestDTO) {
        Task task = helper.findTaskById(taskId);
        Workspace workspace = helper.findWorkspaceById(taskRequestDTO.getWorkspaceId());

        updateTaskDetails(task, taskRequestDTO,workspace);
        taskRepository.save(task);

        return taskMapper.taskToTaskResponseDTO(task);

    }
    public TaskResponseDTO updateTaskStatus(UUID taskId, TaskStatusUpdateDTO taskStatusUpdateDTO) {
        Task task = helper.findTaskById(taskId);
        task.setStatus(TaskStatus.valueOf(taskStatusUpdateDTO.getStatus().toUpperCase()));

        taskRepository.save(task);

        return taskMapper.taskToTaskResponseDTO(task);
    }
    public void deleteTask(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }
    private Task createTaskObject(TaskRequestDTO taskRequestDTO, Workspace workspace){
        TaskStatus status = taskRequestDTO.getStatus()==null? TaskStatus.PENDING : TaskStatus.valueOf(taskRequestDTO.getStatus().toUpperCase());


        return Task.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .status(status)
                .workspace(workspace)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
    private void updateTaskDetails(Task task, TaskRequestDTO taskRequestDTO , Workspace workspace) {
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(TaskStatus.valueOf(taskRequestDTO.getStatus().toUpperCase()));
        task.setWorkspace(workspace);
    }
    private List<TaskResponseDTO> mapTasksToDTOs(List<Task> tasks) {
        return tasks.stream()
                .map(taskMapper::taskToTaskResponseDTO)
                .collect(Collectors.toList());
    }

    private PaginatedResponse<TaskResponseDTO> buildPaginatedResponse(
            List<TaskResponseDTO> tasks, Page<Task> taskPage) {
        return new PaginatedResponse<>(
                tasks,
                taskPage.getTotalElements(),
                taskPage.getTotalPages(),
                taskPage.getNumber()
        );
    }
}
