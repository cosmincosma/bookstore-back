package com.cosmin.bookstoreback.services.impl;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.models.Statistics;
import com.cosmin.bookstoreback.repositories.BookRepository;
import com.cosmin.bookstoreback.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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

    public Map<List<String>, Integer> getNewestBook(List<Book> bookList) {
        Map<List<String>, Integer> newestBookMap = new HashMap<>();
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
        newestBookMap.put(newestBooksName, maxYear);

        return newestBookMap;
    }

    public Map<List<String>, Integer> getOldestBook(List<Book> bookList) {
        Map<List<String>, Integer> oldestBooksMap = new HashMap<>();
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
        oldestBooksMap.put(oldestBooksName, minYear);

        return oldestBooksMap;
    }

    public Map<List<String>, Double> getMostExpensiveBook(List<Book> bookList) {
        Map<List<String>, Double> mostExpensiveBooksMap = new HashMap<>();
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
        mostExpensiveBooksMap.put(mostExpensiveBooksName, maxCost);

        return mostExpensiveBooksMap;
    }

    public Map<List<String>, Double> getCheapestBook(List<Book> bookList) {
        Map<List<String>, Double> mostCheapestBooksMap = new HashMap<>();
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
        mostCheapestBooksMap.put(mostCheapestBooksName, minCost);

        return mostCheapestBooksMap;
    }
}
