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
        //si no existe return un 404
        //si se ha borrado un ok
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);

        if (optionalBook.isPresent()) {
            // Eliminar el libro si existe
            bookRepository.bookdelete(optionalBook.get());
            return ResponseEntity.ok().build(); // Retorna un 200 OK
        }

        // Si no existe, retorna un 404 Not Found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //update=> modificar un libro por su isbn (PUT)
    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {

        Optional<Book> optionalBook = this.bookRepository.findByIsbn(isbn);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            //Aca en esta lineo actualizo los campos del libro existente.
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setTitle(updatedBook.getTitle());

            bookRepository.save(existingBook);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

