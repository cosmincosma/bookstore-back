package com.cosmin.bookstoreback.services.impl;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.repositories.BookRepository;
import com.cosmin.bookstoreback.services.BookService;
import com.cosmin.bookstoreback.utils.Consts;
import com.cosmin.bookstoreback.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final NumberGenerator numberGenerator;
    private final SecurityServiceImpl securityService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, NumberGenerator numberGenerator, SecurityServiceImpl securityService) {
        this.bookRepository = bookRepository;
        this.numberGenerator = numberGenerator;
        this.securityService = securityService;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public List<Book> getAllBooksOrderedByTitleAsc() {
        return bookRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public Integer countBooks() {
        return bookRepository.countBooks();
    }

    @Override
    public Book saveBook(Book book) {
        book.setIsbn(securityService.encrypt(numberGenerator.generateNumber(), Consts.secretKey));
        book.setAvailability(Availability.UNKNOWN);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Override
    public Book updateBookAvailability(Book book, Availability availability) {
        book.setAvailability(availability);
        return bookRepository.save(book);
    }

}
