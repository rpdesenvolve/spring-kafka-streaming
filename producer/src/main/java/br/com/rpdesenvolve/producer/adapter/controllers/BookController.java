package br.com.rpdesenvolve.producer.adapter.controllers;

import br.com.rpdesenvolve.producer.domain.model.Book;
import br.com.rpdesenvolve.producer.port.out.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<List<Book>> createBook(@RequestBody List<Book> bookList) {

        bookList.forEach(bookService::send);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookList);
    }
}
