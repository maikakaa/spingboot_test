package com.example.demo;

import com.example.demo.dto.request.CreateBookRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.BookResponse;
import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createBook(@RequestBody CreateBookRequest createBookRequest,
                                                  HttpServletRequest request){
        String requestId = request.getSession().getId();
        logger.info("[ {} ] received request to create a new book. book payload: {}", requestId, createBookRequest);

        if (!createBookRequest.isValid()) {
            ApiResponse<?> apiResponse = ApiResponse.error(
                    HttpStatus.BAD_REQUEST.value(),
                    "Title and author were not passed with request",
                    ErrorResponse.of(List.of("Title and author are required."), "Please try again.",
                            HttpStatus.BAD_REQUEST.name())
            );
            return ResponseEntity.badRequest().body(apiResponse);
        }

        ApiResponse<?> result = bookService.handleBookCreation(requestId, createBookRequest);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok("Book deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping ("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Update the existing book’s fields
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());

            // Save the updated book back to the repository
            bookRepository.save(existingBook);

            return ResponseEntity.ok(existingBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
