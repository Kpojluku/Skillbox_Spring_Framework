package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import ru.goltsov.education.configuration.properties.AppCacheProperties;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.model.Book;
import ru.goltsov.education.model.Category;
import ru.goltsov.education.repository.BookRepository;
import ru.goltsov.education.repository.CategoryRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@EnableCaching
@CacheConfig(cacheManager = "redisCacheManager")
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @CacheEvict(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, beforeInvocation = true, allEntries = true)
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, beforeInvocation = true, allEntries = true)
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Книга с ID {0} не найдена!", id)));
    }

//    @CacheEvict(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, beforeInvocation = true, allEntries = true)
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, beforeInvocation = true, key = "#updatedBook.name + #updatedBook.author"),
                    @CacheEvict(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, beforeInvocation = true, key = "#updatedBook.category.categoryName")
            }
    )
    public Book update(Book updatedBook) {

        Book book = findById(updatedBook.getId());
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setCategory(updatedBook.getCategory());
        return save(book);

    }

    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, key = "#name + #author")
    public Book findByNameAndAuthor(String name, String author) {
        return bookRepository.findByNameAndAuthor(name, author).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Книга с названием {0} и автором {1} не найдена!", name, author)));
    }

    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_ENTITIES, key = "#categoryName")
    public List<Book> findByCategoryName(String categoryName) {

        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
        if (categoryOptional.isPresent()) {
            return bookRepository.findByCategoryId(categoryOptional.get().getId());
        } else {
            throw new ru.goltsov.education.exception.EntityNotFoundException(MessageFormat.format("Категория с названием {0} не найдена!", categoryName));
        }
    }
}
