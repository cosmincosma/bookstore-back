package com.cosmin.bookstoreback.dtos;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * DTO for responses (ex: get book)
 */
@Getter
@Setter
public class BookDetailsDto {

    private Long id;
    private String title;
    private String description;
    private Date publicationDate;
    private Integer numberOfPages;
    private Language language;
    private Double cost;
    private String imageURL;
    private Availability availability;

}
