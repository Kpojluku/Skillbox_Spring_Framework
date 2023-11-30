package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.model.User;
import ru.goltsov.education.repository.UserRepository;
import ru.goltsov.education.utils.BeenUtils;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Пользователь с ID {0} не найден!", userId)));
    }

    public User update(User updatedUser) {
        User user = findById(updatedUser.getId());

        BeenUtils.copyNonNullProperties(updatedUser, user);

        return save(user);

    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
