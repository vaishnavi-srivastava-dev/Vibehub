package com.myorg.vibehub.service;

import com.myorg.vibehub.dto.response.CountryResponseDto;
import com.myorg.vibehub.model.Country;
import com.myorg.vibehub.repository.CountryRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService{
    @Autowired
    CountryRepositiry countryRepositiry;

    @Override
    public List<CountryResponseDto> getAllCountries() {
        List<Country> countryList = countryRepositiry.findAll();
        List<CountryResponseDto> countryResponseDtoList = new LinkedList<>();

        for(Country country:countryList){
            countryResponseDtoList.add(mapCountryToCountryResponseDto(country));
        }

        return countryResponseDtoList;
    }

    @Override
    public Page<CountryResponseDto> getCountryPage(int pageIndex, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
        Page<Country> countryPage = countryRepositiry.findAll(pageable);

        Page<CountryResponseDto> countryResponseDtoPage = countryPage.map(country -> mapCountryToCountryResponseDto(country));
        return countryResponseDtoPage;
    }

    //HELPER METHODS-

    public CountryResponseDto mapCountryToCountryResponseDto(Country country){
        CountryResponseDto countryResponseDto = new CountryResponseDto();

        countryResponseDto.setId(country.getId());
        countryResponseDto.setCode(country.getCode());
        countryResponseDto.setName(country.getName());
        countryResponseDto.setSlug(country.getSlug());

        return countryResponseDto;
    }
}
