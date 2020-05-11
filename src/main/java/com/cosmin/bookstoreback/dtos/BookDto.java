package com.cosmin.bookstoreback.dtos;

import com.cosmin.bookstoreback.models.Language;
import com.cosmin.bookstoreback.utils.Consts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


/**
 * DTO for requests (ex: save)
 */
@Getter
@Setter
public class BookDto {

    @NotBlank(message = "Title does not respect the field validation rules (2-50 characters).")
    @Size(min = 2, max = 50, message = "Title does not respect the field validation rules (2-50 characters).")
    private String title;

    @Size(min = 2, max = 500, message = "Description does not respect the field validation rules (2-500 characters).")
    @NotBlank(message = "Description does not respect the field validation rules (2-500 characters).")
    private String description;

    @ApiModelProperty(required = true, example = "2020")
    @Max(value = Consts.currentYear, message = "Year does not respect the field validation rules (0-current year).")
    @Min(value = 1, message = "Year does not respect the field validation rules (0-current year).")
    private Short publicationYear;

    @NotNull(message = "Number of pages does not respect the field validation rules. (Minim value: 5).")
    @Min(value = 5, message = "Number of pages does not respect the field validation rules. (Minim value: 5).")
    private Integer numberOfPages;

    private Double cost;

    @NotNull(message = "The language must be selected.")
    private Language language;

    private String imageURL;

}
