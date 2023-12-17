package ru.goltsov.education.mapper;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.model.Book;
import ru.goltsov.education.model.Category;
import ru.goltsov.education.repository.CategoryRepository;
import ru.goltsov.education.web.model.BookRequest;
import ru.goltsov.education.web.model.BookResponse;

import java.util.Optional;

@Component
public class BookMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public Book requestToBook(BookRequest bookRequest) throws EntityNotFoundException {
        Book book = new Book();
        book.setName(bookRequest.getName());
        book.setAuthor(bookRequest.getAuthor());
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(bookRequest.getCategoryName());
        if (categoryOptional.isPresent()) {
            book.setCategory(categoryOptional.get());
        } else {
            categoryRepository.save(new Category(bookRequest.getCategoryName()));
//            throw new EntityNotFoundException("Category with name " + bookRequest.getName() + " not found");
        }
        return book;
    }

    public BookRequest bookToRequest(Book book) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setName(book.getName());
        bookRequest.setAuthor(book.getAuthor());
        bookRequest.setCategoryName(book.getCategory().getCategoryName());
        return bookRequest;
    }

    public BookResponse bookToResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setCategory(book.getCategory());
        return bookResponse;
    }

    public Book responseToBook(BookResponse bookResponse) {
        Book book = new Book();
        book.setId(bookResponse.getId());
        book.setName(bookResponse.getName());
        book.setAuthor(bookResponse.getAuthor());
        book.setCategory(bookResponse.getCategory());
        return book;
    }

}
