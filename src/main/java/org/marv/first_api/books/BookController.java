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

   /* @PostMapping
    public Book createBook(@RequestBody Book book) {
        //comprobar que no exista el isbn y si existe que return (bad_request)
        bookRepository.save(book);
        return book; //200 or created 201
    }*/

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
    public void deleteBook(@PathVariable String isbn) {
        //si no existe return un 404
        //si se ha borrado un ok
        bookRepository.deleteBook(isbn);


    }
    //update=> modificar un libro por su isbn (PUT)
    @PutMapping("/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        // Buscar el libro existente por su ISBN
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Actualizar los campos del libro existente con los valores del libro actualizado
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            //existingBook.setIsbn(book.getIsbn()); // Si se permite cambiar el ISBN

            // Guardar los cambios en el repositorio
            bookRepository.update(existingBook);

            // Respuesta de éxito
            return new ResponseEntity<>("Libro actualizado correctamente", HttpStatus.OK);
        }

        // Si no se encuentra el libro, retornar un error 404
        return new ResponseEntity<>("Libro no encontrado", HttpStatus.NOT_FOUND);
    }

}

