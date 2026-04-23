package com.myorg.vibehub.repository;

import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.enums.PageCategory;
import com.myorg.vibehub.model.SocialMediaPage;
import com.myorg.vibehub.model.User;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PageRepository extends JpaRepository<SocialMediaPage,Long> {
    //Custom Finder Methods
    List<SocialMediaPage>findByNameContaining(String name);
    List<SocialMediaPage> findByNameContainingAndCategory(String name, PageCategory category);

    //JPQL
    @Query("SELECT p FROM SocialMediaPage p WHERE p.category = :category")
    List<SocialMediaPage> searchSocialMediaPageByCategory(@Param("category") PageCategory category);

    @Query("SELECT p FROM SocialMediaPage p WHERE p.name LIKE %:name% AND p.description LIKE %:description%")
    List<SocialMediaPage> searchSocialMediaPageByNameAndDescription(@Param("name")String name,@Param("description")String description);

    //Native Queries
    @Query(value = "SELECT * FROM social_media_pages p WHERE p.category = :category",nativeQuery = true)
    List<SocialMediaPage> searchSocialMediaPageByCategoryUsingNativeQuery(@Param("category") String category);
}

    /*
    private Map<Long, SocialMediaPage> pages = new HashMap<>();

    private static Long currentId=0L;

    public SocialMediaPage addPage(SocialMediaPage p){
        Long id=++currentId;
        p.setId(id);
        pages.put(id,p);
        return pages.get(id);
    }

    public SocialMediaPage getPage(Long id){
        return pages.get(id);
    }

    public List<SocialMediaPage> getAllPages(){
        //we took all values of "pages" map and converted it to a linked list
        return new LinkedList<>(pages.values());
    }
    public SocialMediaPage updatePage(SocialMediaPage p){
        if(p.getId()!=null){
            pages.put(p.getId(),p);
        }
        return pages.get(p.getId());
    }
    public void removePage(Long id){
        pages.remove(id);
    }

     */

