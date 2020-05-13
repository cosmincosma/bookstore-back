package com.cosmin.bookstoreback.services;

import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.models.Statistics;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    Statistics createStatistics();

    Integer getNumberOfBooks(List<Book> bookList);

    Integer getNumberOfAvailableBooks(List<Book> bookList);

    Map<List<String>, Integer> getNewestBook(List<Book> bookList);

    Map<List<String>, Integer> getOldestBook(List<Book> bookList);

    Map<List<String>, Double> getMostExpensiveBook(List<Book> bookList);

    Map<List<String>, Double> getCheapestBook(List<Book> bookList);

}
