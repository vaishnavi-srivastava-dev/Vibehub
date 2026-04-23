package com.myorg.vibehub.model;

import jakarta.persistence.*;
import lombok.Data;

    @Data
    @Entity
    @Table(name = "countries")
    public class Country {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String name;

        @Column(unique = true,nullable = false)
        private String slug;

        private Long code;
    }

