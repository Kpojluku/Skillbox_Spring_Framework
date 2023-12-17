package ru.goltsov.education.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.goltsov.education.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByNameAndAuthor(String name, String author);

    List<Book> findByCategoryId(Long id);

    @EntityGraph(attributePaths = {"category"})
    List<Book> findAll();
}
