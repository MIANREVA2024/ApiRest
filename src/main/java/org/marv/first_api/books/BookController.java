package org.marv.first_api.books;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;
    private String isbn;


    public BookController() {
        this.bookRepository = new InMemoryBookRepository();

    }

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();

    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookIsbn(@PathVariable String isbn) {
       Optional<Book> optionalBook = this.bookRepository.findByIsbn(isbn);

       if (optionalBook.isPresent()) {
         return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK); //200 ok
       }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND); //400 ok
    }

     @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Optional<Book> optionalBook = this.bookRepository.findByIsbn(book.getIsbn());
        if (optionalBook.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);

    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Book> deleteBook(@PathVariable String isbn){

        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);

        if (optionalBook.isPresent()) {

            bookRepository.deleteBook(optionalBook.get());
            return ResponseEntity.ok().build();
        }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book book) {

        Optional<Book> optionalBook = this.bookRepository.findByIsbn(isbn);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            existingBook.setAuthor(book.getAuthor());
            existingBook.setTitle(book.getTitle());

            bookRepository.save(existingBook);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

