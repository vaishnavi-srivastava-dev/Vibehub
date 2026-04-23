package com.myorg.vibehub.model;

import com.myorg.vibehub.enums.PageCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "social_media_pages")
public class SocialMediaPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private PageCategory category;
    private String description;
    private LocalDate createdOn;
    private String password;
}
