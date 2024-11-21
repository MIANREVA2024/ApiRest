package org.marv.first_api.books;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {
    private final static List<Book> booksBD = new ArrayList<>();

    public InMemoryBookRepository() {
        booksBD.add(new Book("B001", "java","miguel"));
        booksBD.add(new Book("B002", "javascript","Miguel Angel"));
    }

    @Override
    public List<Book> findAll() {
        return Collections.unmodifiableList(booksBD);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        for (Book book : booksBD) {
            if (book.getIsbn().equals(isbn))
                return Optional.of(book);
        }
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        booksBD.add(book);
    }

    @Override
    public void bookdelete(Book book) {
        booksBD.removeIf(b -> b.getIsbn().equals(book.getIsbn()));
    }






}