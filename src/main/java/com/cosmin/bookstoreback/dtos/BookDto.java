package com.cosmin.bookstoreback.dtos;

import com.cosmin.bookstoreback.models.Language;
import com.cosmin.bookstoreback.utils.Consts;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * DTO for requests (ex: save)
 */
@Getter
@Setter
public class BookDto {

    @NotBlank
    @Size(min=2, max=50, message= "Title does not respect the field validation rules (2-50 characters).")
    private String title;

    @Size(min=2,max=500, message= "Description does not respect the field validation rules (2-500 characters).")
    private String description;

    @ApiModelProperty(required = true, example = "01-01-2020")
    @JsonFormat(pattern = Consts.DATE_FORMAT)
    private Date publicationDate;

    @NotNull
    @Min(value = 5, message= "Number of pages does not respect the field validation rules. (Minim value: 5).")
    private Integer numberOfPages;

    private Double cost;

    private Language language;

    private String imageURL;

}
