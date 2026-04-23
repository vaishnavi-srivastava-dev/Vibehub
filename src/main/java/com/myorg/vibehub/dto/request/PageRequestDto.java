package com.myorg.vibehub.dto.request;

import com.myorg.vibehub.enums.PageCategory;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PageRequestDto {
    private String name;
    private PageCategory category;
    private String description;
    private LocalDate createdOn;
    private String password;
}
