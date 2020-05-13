package com.cosmin.bookstoreback.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Statistics {

    private Integer numberOfBooks;
    private Integer numberOfAvailableBooks;
    private Map<List<String>, Integer> oldestBooks;
    private Map<List<String>, Integer> newestBooks;
    private Map<List<String>, Double> mostExpensiveBooks;
    private Map<List<String>, Double> cheapestBooks;

    @Override
    public String toString() {
        return "Statistics{" +
                "\n numberOfBooks=" + numberOfBooks +
                "\n numberOfAvailableBooks=" + numberOfAvailableBooks +
                "\n oldestBooks=" + oldestBooks +
                "\n newestBooks=" + newestBooks +
                "\n mostExpensiveBooks=" + mostExpensiveBooks +
                "\n cheapestBooks=" + cheapestBooks + '}';
    }
}


