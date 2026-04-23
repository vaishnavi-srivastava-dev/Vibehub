package com.myorg.vibehub.dto.request;

import lombok.Data;

@Data
public class CountryRequestDto {
    private String name;
    private String slug;
    private Long code;
}
