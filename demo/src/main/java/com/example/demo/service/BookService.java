package com.example.demo.service;

import com.example.demo.dto.request.CreateBookRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public ApiResponse<?> handleBookCreation(String requestId, CreateBookRequest createBookRequest) {
        Book book = new Book();

        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());

        boolean isSuccessful = false;

        try {
            book = bookRepository.save(book);
            isSuccessful = true;
        } catch (Exception e) {
            logger.error("[ {} ] an error occurred while persisting a book. message: {}", requestId, e.getMessage(), e);
        }

        if (isSuccessful) {
            return ApiResponse.success("book created successfully", book.toResponse());
        }

        return ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred while creating a new book",
                ErrorResponse.of(List.of("Book creation failed."), "Please try again.",
                        HttpStatus.INTERNAL_SERVER_ERROR.name())
        );
    }
}
