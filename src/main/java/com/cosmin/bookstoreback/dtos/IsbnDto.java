package com.cosmin.bookstoreback.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class IsbnDto {

    @NotBlank
    @Pattern(regexp = "[9][4][3][-]([0-9])[-]([0-9]{5})*$", message = "ISBN number does not respect the field validation rules. Pattern: 943-x-xxxxx")
    private String isbn;

}
