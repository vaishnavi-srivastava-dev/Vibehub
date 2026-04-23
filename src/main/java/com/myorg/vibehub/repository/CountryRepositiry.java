package com.myorg.vibehub.repository;

import com.myorg.vibehub.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepositiry extends JpaRepository<Country,Long> {
}
