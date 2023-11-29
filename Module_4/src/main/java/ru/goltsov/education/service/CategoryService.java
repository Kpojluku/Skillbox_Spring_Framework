package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.goltsov.education.exception.AlreadyExistsException;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.model.NewsCategory;
import ru.goltsov.education.repository.CategoryRepository;
import ru.goltsov.education.repository.specification.NewsCategorySpecification;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<NewsCategory> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public List<NewsCategory> filterByCategory(String category) {
        return categoryRepository.findAll(NewsCategorySpecification.byCategory(category));
    }

    public NewsCategory findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Категория с ID {0} не найдена!", id)));
    }

    public NewsCategory save(NewsCategory category) {
        List<NewsCategory> newsCategories = filterByCategory(category.getCategory());
        if (newsCategories.isEmpty()) {
            return categoryRepository.save(category);
        } else {
            throw new AlreadyExistsException("Категория: '%s' уже существует".formatted(category.getCategory()));
        }
    }

}
