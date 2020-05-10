package com.cosmin.bookstoreback.services;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Book;

import java.util.List;

public interface BookService {

    Book getBookById(Long id);

    List<Book> getAllBooksOrderedByTitleAsc();

    Integer countBooks();

    Book saveBook(Book book);

    void deleteBookById(Long id);

    Book getBookByTitle(String title);

    Book updateBookAvailability(Book book, Availability availability);
}
