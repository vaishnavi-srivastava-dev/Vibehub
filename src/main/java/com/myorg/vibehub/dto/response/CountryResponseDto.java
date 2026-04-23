package com.myorg.vibehub.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CountryResponseDto {
    private Long id;
    private String name;
    private String slug;
    private Long code;
}
