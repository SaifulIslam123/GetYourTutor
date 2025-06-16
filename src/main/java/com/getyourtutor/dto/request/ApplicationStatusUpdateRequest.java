package com.getyourtutor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusUpdateRequest {
    @NotBlank
    private String status; // "ACCEPTED" or "REJECTED"
}
