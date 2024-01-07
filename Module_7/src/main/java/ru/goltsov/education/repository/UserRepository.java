package ru.goltsov.education.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.goltsov.education.entity.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {


}
