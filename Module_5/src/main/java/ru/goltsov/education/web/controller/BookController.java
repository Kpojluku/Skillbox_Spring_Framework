package ru.goltsov.education.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.mapper.BookMapper;
import ru.goltsov.education.model.Book;
import ru.goltsov.education.service.BookService;
import ru.goltsov.education.web.model.BookRequest;
import ru.goltsov.education.web.model.BookResponse;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    // Метод для получения всех книг
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks() {
        return bookService.findAll().stream().map(bookMapper::bookToResponse).toList();
    }

    // Метод для получения книги по ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getBookById(@PathVariable Long id) {
        return bookMapper.bookToResponse(bookService.findById(id));
    }

    @GetMapping("/nameAndAuthor")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse findBookByNameAndAuthor(@RequestParam("name") String name, @RequestParam("author") String author)  {
        return bookMapper.bookToResponse(bookService.findByNameAndAuthor(name, author));
    }

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> findBooksByCategory(@RequestParam("category") String categoryName) {
        return bookService.findByCategoryName(categoryName).stream().map(bookMapper::bookToResponse).toList();
    }

    // Метод для создания новой книги
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@RequestBody BookRequest book) throws EntityNotFoundException {
        return bookMapper.bookToResponse(bookService.save(bookMapper.requestToBook(book)));
    }

    // Метод для обновления информации о книге
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(@PathVariable Long id, @RequestBody BookRequest book) throws EntityNotFoundException {
        Book existingBook = bookService.findById(id);
        if (existingBook != null) {
            Book updatedBook = bookMapper.requestToBook(book);
            updatedBook.setId(id);
            return bookMapper.bookToResponse(bookService.update(updatedBook));
        } else {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
    }

    // Метод для удаления книги
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}
