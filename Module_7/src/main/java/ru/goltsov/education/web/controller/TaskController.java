package ru.goltsov.education.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.goltsov.education.mapper.TaskMapper;
import ru.goltsov.education.service.TaskService;
import ru.goltsov.education.web.model.TaskRequest;
import ru.goltsov.education.web.model.TaskResponse;

import java.util.Set;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Flux<TaskResponse> getAllTasks() {
        return taskService.findAll().map(taskMapper::taskToTaskResponse);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<TaskResponse>> getById(@PathVariable String id) {
        return taskService.findById(id)
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TaskResponse>> createTask(@RequestBody TaskRequest taskRequest) {
        return taskService.save(taskMapper.taskRequestToTask(taskRequest))
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<TaskResponse>> updateItem(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
        return taskService.update(id, taskMapper.taskRequestToTask(taskRequest))
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/addObserver/{id}")
    public Mono<ResponseEntity<TaskResponse>> addObserver(@PathVariable String id, @RequestParam String observerId) {
        return taskService.addObserver(id, observerId)
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
