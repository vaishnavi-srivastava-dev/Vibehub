package com.myorg.vibehub.dto.response;

import com.myorg.vibehub.enums.PageCategory;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PageResponseDto {
    private Long id;
    private String name;
    private PageCategory category;
    private String description;
    private LocalDate createdOn;
}
