package com.cosmin.bookstoreback.dtos;

import com.cosmin.bookstoreback.models.Availability;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AvailabilityDto {

    @Valid
    private IsbnDto isbnDto;

    @NotNull
    private Availability availability;

}
