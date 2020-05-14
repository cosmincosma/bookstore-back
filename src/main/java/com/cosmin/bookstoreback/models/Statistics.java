package com.cosmin.bookstoreback.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {

    private Integer numberOfBooks;
    private Integer numberOfAvailableBooks;
    private StatisticsInfo oldestBooks;
    private StatisticsInfo newestBooks;
    private StatisticsInfo mostExpensiveBooks;
    private StatisticsInfo cheapestBooks;

}


