package com.myorg.vibehub.service;

import com.myorg.vibehub.dto.request.PageRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.PageResponseDto;
import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.enums.PageCategory;
import org.hibernate.query.Page;

import java.util.List;

public interface PageService {

    PageResponseDto addPage(PageRequestDto pageRequestDto);
    PageResponseDto getPage(Long id);
    List<PageResponseDto> getAllPages();
    PageResponseDto updatePage(Long id,PageRequestDto pageRequestDto);
    GenericResponseDto removePage(Long id);
    List<PageResponseDto> searchByName(String name);
    List<PageResponseDto> searchByNameAndCategory(String name, PageCategory category);

    //JPQL
    List<PageResponseDto> searchSocialMediaPageByCategory(PageCategory category);
    List<PageResponseDto> searchSocialMediaPageByNameAndDescription(String name, String description);

    //Native Queries
    List<PageResponseDto> searchSocialMediaPageByCategoryUsingNativeQuery(PageCategory category);

}
