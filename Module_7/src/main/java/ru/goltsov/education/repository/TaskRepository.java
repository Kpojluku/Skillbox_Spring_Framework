package ru.goltsov.education.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.goltsov.education.entity.Task;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {



}
