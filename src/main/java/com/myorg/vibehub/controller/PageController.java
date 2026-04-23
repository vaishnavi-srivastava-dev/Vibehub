package com.myorg.vibehub.controller;

import com.myorg.vibehub.dto.request.PageRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.PageResponseDto;
import com.myorg.vibehub.dto.response.UserResponseDto;
import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.enums.PageCategory;
import com.myorg.vibehub.service.PageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController
{
    @Autowired
    private PageService pageService;

    @PostMapping()
    public ResponseEntity<PageResponseDto> registerSocialMediaPage(@RequestBody PageRequestDto pageRequestDto){
        return new ResponseEntity<>(pageService.addPage(pageRequestDto), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageResponseDto> getSocialMediaPage(@PathVariable Long id){
        return new ResponseEntity<>(pageService.getPage(id),HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<PageResponseDto>> getAllSocialMediaPages(){
        return new ResponseEntity<>(pageService.getAllPages(), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PageResponseDto> updateSocialMediaPages(@PathVariable Long id, @RequestBody PageRequestDto pageRequestDto){
        return new ResponseEntity<>(pageService.updatePage(id,pageRequestDto),HttpStatusCode.valueOf(200));
    }

    @DeleteMapping
    public ResponseEntity<GenericResponseDto> removeSocialMediaPage(@RequestParam Long id){
        return new ResponseEntity<>(pageService.removePage(id),HttpStatusCode.valueOf(200));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<PageResponseDto>> searchByName(@PathVariable String name) {
        return new ResponseEntity<>(pageService.searchByName(name), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/search/params")
    public ResponseEntity<List<PageResponseDto>> searchByNameAndCategory(@RequestParam String name, @RequestParam PageCategory category) {
        return new ResponseEntity<>(pageService.searchByNameAndCategory(name,category), HttpStatusCode.valueOf(200));
    }

    //JPQL
    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<PageResponseDto>> searchSocialMediaPageByCategory(@PathVariable PageCategory category){
        return new ResponseEntity<>(pageService.searchSocialMediaPageByCategory(category),HttpStatusCode.valueOf(200));
    }

    @GetMapping("/search/params/name-description")
    public ResponseEntity<List<PageResponseDto>> searchSocialMediaPageByNameAndDescription(@RequestParam String name, String description){
        return new ResponseEntity<>(pageService.searchSocialMediaPageByNameAndDescription(name,description),HttpStatusCode.valueOf(200));
    }

    //Native Query
    @GetMapping("/search/native-sql/category/{category}")
    public ResponseEntity<List<PageResponseDto>> searchSocialMediaPageByCategoryUsingNativeQuery(@PathVariable PageCategory category){
        return new ResponseEntity<>(pageService.searchSocialMediaPageByCategoryUsingNativeQuery(category),HttpStatusCode.valueOf(200));
    }
}
