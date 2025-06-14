package com.getyourtutor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTypeRequest {

    @NotBlank
    private String name;

    private String description;
}
