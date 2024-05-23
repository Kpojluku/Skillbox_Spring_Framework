package ru.goltsov.education.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.goltsov.education.mapper.TaskMapper;
import ru.goltsov.education.service.TaskService;
import ru.goltsov.education.web.model.TaskRequest;
import ru.goltsov.education.web.model.TaskResponse;

import java.security.Principal;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Flux<TaskResponse> getAllTasks() {
        return taskService.findAll().map(taskMapper::taskToTaskResponse);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<ResponseEntity<TaskResponse>> getById(@PathVariable String id) {
        return taskService.findById(id)
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> createTask(@RequestBody TaskRequest taskRequest,
                                                         @AuthenticationPrincipal Mono<Principal> principalMono) {
        return taskService.save(taskMapper.taskRequestToTask(taskRequest), principalMono)
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> updateItem(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
        return taskService.update(id, taskMapper.taskRequestToTask(taskRequest))
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/addObserver/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<ResponseEntity<TaskResponse>> addObserver(@PathVariable String id, @RequestParam String observerId) {
        return taskService.addObserver(id, observerId)
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
