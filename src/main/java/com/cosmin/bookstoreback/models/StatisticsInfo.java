package com.cosmin.bookstoreback.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticsInfo {

    private Object value;
    private List<String> books;

    public StatisticsInfo(Object value, List<String> books) {
        this.value = value;
        this.books = books;
    }

}
