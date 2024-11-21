package org.marv.first_api.books;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

   Optional <Book> findByIsbn(String isbn);

    void save(Book book);


    void bookdelete(Book book);



}
