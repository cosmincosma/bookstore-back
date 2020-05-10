package com.cosmin.bookstoreback.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(length = 500)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "publication_date")
    private Date publicationDate;


    @Column(name = "number_of_pages", nullable = false)
    private Integer numberOfPages;

    @Column
    private Double cost;

    @Column
    private Language language;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(name = "image_url")
    private String imageURL;

    @Column
    private Availability availability;

}

