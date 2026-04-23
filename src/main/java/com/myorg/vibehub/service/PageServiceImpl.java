package com.myorg.vibehub.service;

import com.myorg.vibehub.dto.request.PageRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.PageResponseDto;
import com.myorg.vibehub.dto.response.UserResponseDto;
import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.enums.PageCategory;
import com.myorg.vibehub.model.SocialMediaPage;
import com.myorg.vibehub.model.User;
import com.myorg.vibehub.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    PageRepository pageRepository;

    @Override
    public PageResponseDto addPage(PageRequestDto pageRequestDto) {

        SocialMediaPage socialMediaPage = new SocialMediaPage();

        //convert PageRequestDto to Social Media Page
        mapPageRequestDtoToSocialMediaPage(pageRequestDto,socialMediaPage);
        //socialMediaPage= pageRepository.addPage(socialMediaPage);
        SocialMediaPage storedSocialMediaPage=pageRepository.save(socialMediaPage);

        return mapSocialMediaPageToPageResponseDto(storedSocialMediaPage);
    }

    @Override
    public PageResponseDto getPage(Long id) {
        SocialMediaPage socialMediaPage=pageRepository.findById(id).orElse(null);
        return mapSocialMediaPageToPageResponseDto(socialMediaPage);
    }

    @Override
    public List<PageResponseDto> getAllPages() {
        List<SocialMediaPage> pageList = pageRepository.findAll();
        List<PageResponseDto> pageResponseDtoList = new LinkedList<>();

        for(SocialMediaPage socialMediaPage:pageList) {
            PageResponseDto pageResponseDto= mapSocialMediaPageToPageResponseDto(socialMediaPage);
            pageResponseDtoList.add(pageResponseDto);
        }
        return pageResponseDtoList;
    }

    @Override
    public PageResponseDto updatePage(Long id, PageRequestDto pageRequestDto) {

        SocialMediaPage socialMediaPage = pageRepository.findById(id).orElse(null);
        mapPageRequestDtoToSocialMediaPage(pageRequestDto,socialMediaPage);
        pageRepository.save(socialMediaPage);
        return mapSocialMediaPageToPageResponseDto(socialMediaPage);
    }

    @Override
    public GenericResponseDto removePage(Long id) {
        SocialMediaPage socialMediaPage = pageRepository.findById(id).orElse(null);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        if(socialMediaPage != null){
            String name= socialMediaPage.getName();
            genericResponseDto.setIsSuccess(true);
            genericResponseDto.setMessage("Page name- " + name + " is removed successfully");
        }
        else{
            genericResponseDto.setIsSuccess(false);
            genericResponseDto.setMessage("The page id- " + " does not exist!");
        }
        return genericResponseDto;
    }

    @Override
    public List<PageResponseDto> searchByName(String name) {
        List<SocialMediaPage> socialMediaPageList = pageRepository.findByNameContaining(name);
        List<PageResponseDto> socialMediaPageResponseDtoList = new LinkedList<>();

        for(SocialMediaPage socialMediaPage : socialMediaPageList){
            socialMediaPageResponseDtoList.add(mapSocialMediaPageToPageResponseDto(socialMediaPage));
        }
        return socialMediaPageResponseDtoList;
    }

    @Override
    public List<PageResponseDto> searchByNameAndCategory(String name, PageCategory category) {
        List<SocialMediaPage> socialMediaPageList = pageRepository.findByNameContainingAndCategory(name,category);
        List<PageResponseDto> socialMediaPageResponseDtoList = new LinkedList<>();

        for(SocialMediaPage socialMediaPage : socialMediaPageList)
        {
            socialMediaPageResponseDtoList.add(mapSocialMediaPageToPageResponseDto(socialMediaPage));
        }
        return socialMediaPageResponseDtoList;
    }

    //JPQL

    @Override
    public List<PageResponseDto> searchSocialMediaPageByCategory(PageCategory category) {
        List<SocialMediaPage> socialMediaPageList = pageRepository.searchSocialMediaPageByCategory(category);
        return mapSocialMediaPageListToPageResponseDtoList(socialMediaPageList);
    }

    @Override
    public List<PageResponseDto> searchSocialMediaPageByNameAndDescription(String name, String description) {
        List<SocialMediaPage> socialMediaPageList = pageRepository.searchSocialMediaPageByNameAndDescription(name,description);
        return mapSocialMediaPageListToPageResponseDtoList(socialMediaPageList);
    }

    @Override
    public List<PageResponseDto> searchSocialMediaPageByCategoryUsingNativeQuery(PageCategory category) {
        List<SocialMediaPage> socialMediaPageList = pageRepository.searchSocialMediaPageByCategoryUsingNativeQuery(category.name());
        return mapSocialMediaPageListToPageResponseDtoList(socialMediaPageList);
    }


    //HELPER METHODS-

    private SocialMediaPage mapPageRequestDtoToSocialMediaPage(PageRequestDto pageRequestDto,SocialMediaPage socialMediaPage)
    {
        socialMediaPage.setName(pageRequestDto.getName());
        socialMediaPage.setCategory(pageRequestDto.getCategory());
        socialMediaPage.setDescription(pageRequestDto.getDescription());
        socialMediaPage.setCreatedOn(pageRequestDto.getCreatedOn());
        socialMediaPage.setPassword(pageRequestDto.getPassword());

        return socialMediaPage;
    }

    private PageResponseDto mapSocialMediaPageToPageResponseDto(SocialMediaPage socialMediaPage)
    {
        PageResponseDto pageResponseDto=new PageResponseDto();
        pageResponseDto.setId(socialMediaPage.getId());
        pageResponseDto.setName(socialMediaPage.getName());
        pageResponseDto.setCategory(socialMediaPage.getCategory());
        pageResponseDto.setDescription(socialMediaPage.getDescription());
        pageResponseDto.setCreatedOn(socialMediaPage.getCreatedOn());

        return pageResponseDto;
    }

    private List<PageResponseDto> mapSocialMediaPageListToPageResponseDtoList(List<SocialMediaPage> socialMediaPageList){
        List<PageResponseDto> socialMediaPageResponseDtoList = new LinkedList<>();

        for(SocialMediaPage socialMediaPage : socialMediaPageList)
        {
            socialMediaPageResponseDtoList.add(mapSocialMediaPageToPageResponseDto(socialMediaPage));
        }
        return socialMediaPageResponseDtoList;
    }

}
