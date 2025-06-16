package com.getyourtutor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceTypeResponse {
    private Long serviceTypeId;
    private String name;
    private String description;
}
