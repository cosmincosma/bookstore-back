package com.cosmin.bookstoreback.services.impl;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.models.Statistics;
import com.cosmin.bookstoreback.models.StatisticsInfo;
import com.cosmin.bookstoreback.repositories.BookRepository;
import com.cosmin.bookstoreback.services.StatisticsService;
import com.cosmin.bookstoreback.utils.StatisticsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsServiceImpl implements StatisticsService {

    private final BookRepository bookRepository;

    @Autowired
    public StatisticsServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Statistics createStatistics() {
        Statistics statistics = new Statistics();
        List<Book> bookList = bookRepository.findAllByOrderByTitleAsc();

        if (bookList == null || bookList.isEmpty()) {
            throw new StatisticsException();
        }

        statistics.setNumberOfBooks(getNumberOfBooks(bookList));
        statistics.setNumberOfAvailableBooks(getNumberOfAvailableBooks(bookList));
        statistics.setNewestBooks(getNewestBook(bookList));
        statistics.setOldestBooks(getOldestBook(bookList));
        statistics.setMostExpensiveBooks(getMostExpensiveBook(bookList));
        statistics.setCheapestBooks(getCheapestBook(bookList));

        return statistics;
    }

    public Integer getNumberOfBooks(List<Book> bookList) {
        return bookList.size();
    }

    public Integer getNumberOfAvailableBooks(List<Book> bookList) {
        List<Book> availableBooks = new ArrayList<>();

        for (Iterator<Book> bookIterator = bookList.iterator(); bookIterator.hasNext(); ) {
            Book book = bookIterator.next();
            if (book.getAvailability() == Availability.AVAILABLE
                    || book.getAvailability() == Availability.UNKNOWN) {
                availableBooks.add(book);
            }
        }

        return availableBooks.size();
    }

    @SuppressWarnings("DuplicatedCode")
    public StatisticsInfo getNewestBook(List<Book> bookList) {
        List<String> newestBooksName = new ArrayList<>();

        int maxYear = bookList.stream()
                .max(Comparator.comparing(Book::getPublicationYear))
                .get()
                .getPublicationYear();

        List<Book> newestBookList = bookList.stream()
                .filter(m -> m.getPublicationYear() == maxYear)
                .collect(Collectors.toList());

        for (Iterator<Book> bookIterator = newestBookList.iterator(); bookIterator.hasNext(); ) {
            Book book = bookIterator.next();
            newestBooksName.add(book.getTitle());
        }

        return new StatisticsInfo(maxYear, newestBooksName);
    }

    @SuppressWarnings("DuplicatedCode")
    public StatisticsInfo getOldestBook(List<Book> bookList) {
        List<String> oldestBooksName = new ArrayList<>();

        int minYear = bookList.stream()
                .min(Comparator.comparing(Book::getPublicationYear))
                .get()
                .getPublicationYear();

        List<Book> oldestBooksList = bookList.stream()
                .filter(m -> m.getPublicationYear() == minYear)
                .collect(Collectors.toList());

        for (Iterator<Book> bookIterator = oldestBooksList.iterator(); bookIterator.hasNext(); ) {
            Book book = bookIterator.next();
            oldestBooksName.add(book.getTitle());
        }

        return new StatisticsInfo(minYear, oldestBooksName);
    }

    @SuppressWarnings("DuplicatedCode")
    public StatisticsInfo getMostExpensiveBook(List<Book> bookList) {
        List<String> mostExpensiveBooksName = new ArrayList<>();

        double maxCost = bookList.stream()
                .max(Comparator.comparing(Book::getCost))
                .get()
                .getCost();

        List<Book> mostExpensiveBooksList = bookList.stream()
                .filter(m -> m.getCost() == maxCost)
                .collect(Collectors.toList());

        for (Iterator<Book> bookIterator = mostExpensiveBooksList.iterator(); bookIterator.hasNext(); ) {
            Book book = bookIterator.next();
            mostExpensiveBooksName.add(book.getTitle());
        }

        return new StatisticsInfo(maxCost, mostExpensiveBooksName);
    }

    @SuppressWarnings("DuplicatedCode")
    public StatisticsInfo getCheapestBook(List<Book> bookList) {
        List<String> mostCheapestBooksName = new ArrayList<>();

        double minCost = bookList.stream()
                .min(Comparator.comparing(Book::getCost))
                .get()
                .getCost();

        List<Book> mostCheapestBooksList = bookList.stream()
                .filter(m -> m.getCost() == minCost)
                .collect(Collectors.toList());

        for (Iterator<Book> bookIterator = mostCheapestBooksList.iterator(); bookIterator.hasNext(); ) {
            Book book = bookIterator.next();
            mostCheapestBooksName.add(book.getTitle());
        }

        return new StatisticsInfo(minCost, mostCheapestBooksName);
    }
}
