package com.cosmin.bookstoreback.dtos;

import com.cosmin.bookstoreback.models.Availability;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AvailabilityDto {

    @NotNull(message = "Availability must be selected")
    private Availability availability;

}
