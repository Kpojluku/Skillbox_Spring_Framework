package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.goltsov.education.entity.Task;
import ru.goltsov.education.entity.User;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.repository.TaskRepository;
import ru.goltsov.education.repository.UserRepository;

import java.security.Principal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public Flux<Task> findAll() {
        return taskRepository.findAll()
                .flatMap(task -> {
                    Mono<User> authorMono = userRepository.findById(task.getAuthorId());
                    Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId());
                    Flux<User> observersFlux = userRepository.findAllById(task.getObserverIds());

                    return Mono.zip(authorMono, assigneeMono, observersFlux.collectList())
                            .map(tuple -> {
                                User author = tuple.getT1();
                                User assignee = tuple.getT2();
                                List<User> observers = tuple.getT3();

                                task.setAuthor(author);
                                task.setAssignee(assignee);
                                task.setObservers(new HashSet<>(observers));

                                return task;
                            });
                });
    }


    public Mono<Task> save(Task task, Mono<Principal> principalMono) {
        task.setId(UUID.randomUUID().toString());
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());

        Mono<Task> newTaskMono = taskRepository.save(task);
//        Mono<User> authorMono = userRepository.findById(task.getAuthorId())
        Mono<User> authorMono = principalMono.map(Principal::getName).flatMap(userRepository::findByUsername)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author not found")));
        Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Assignee not found")));
        Flux<User> users = userRepository.findAll();
        Flux<User> observerUsers = users.filter(user -> task.getObserverIds().contains(user.getId()));

        return Mono.zip(newTaskMono, authorMono, assigneeMono)
                .flatMap(tuple -> {
                    Task task1 = tuple.getT1();
                    User author = tuple.getT2();
                    User assignee = tuple.getT3();
                    task1.setAuthor(author);
                    task1.setAssignee(assignee);
                    return observerUsers.collect(Collectors.toSet())
                            .map(observers -> {
                                task1.setObservers(observers);
                                return task1;
                            });
                });
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }


    public Mono<Task> findById(String id) {
        return taskRepository.findById(id)
                .flatMap(task -> {
                    Mono<User> authorMono = userRepository.findById(task.getAuthorId());
                    Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId());
                    Flux<User> observersFlux = userRepository.findAllById(task.getObserverIds());

                    return Mono.zip(authorMono, assigneeMono, observersFlux.collectList())
                            .map(tuple -> {
                                User author = tuple.getT1();
                                User assignee = tuple.getT2();
                                List<User> observers = tuple.getT3();

                                task.setAuthor(author);
                                task.setAssignee(assignee);
                                task.setObservers(new HashSet<>(observers));

                                return task;
                            });
                });
    }

    public Mono<Task> update(String id, Task task) {
        return findById(id).flatMap(taskForUpdate -> {
            if (StringUtils.hasText(task.getName())) {
                taskForUpdate.setName(task.getName());
            }
            if (StringUtils.hasText(task.getDescription())) {
                taskForUpdate.setDescription(task.getDescription());
            }
            if (task.getStatus() != null) {
                taskForUpdate.setStatus(task.getStatus());
            }
            if (StringUtils.hasText(task.getAssigneeId())) {
                taskForUpdate.setAssigneeId(task.getAssigneeId());
            }
            if (StringUtils.hasText(task.getAuthorId())) {
                taskForUpdate.setAuthorId(task.getAuthorId());
            }
            if (task.getObserverIds() != null) {
                taskForUpdate.setObserverIds(task.getObserverIds());
            }
            taskForUpdate.setUpdatedAt(Instant.now());
            return taskRepository.save(taskForUpdate);
        });
    }

    public Mono<Task> addObserver(String id, String observerId) {
        return userRepository.findById(observerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found")))
                .flatMap(user -> findById(id).flatMap(task -> {
                    if (task.getObserverIds() == null) {
                        task.setObserverIds(new HashSet<>());
                    }
                    task.addObserver(user);
                    task.setUpdatedAt(Instant.now());
                    return taskRepository.save(task);
                }));
    }
}
