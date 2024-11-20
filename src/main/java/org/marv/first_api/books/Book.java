package org.marv.first_api.books;

public class Book {
    private String isbn;
    private String title;
    private String author;


    public Book(String isbn,String title, String author) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String author) {
    }

    public void setAuthor(String author) {
        
    }
}
