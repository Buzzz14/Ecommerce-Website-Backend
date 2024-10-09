package com.buzz.repository;

import com.buzz.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Optional<Category> findFirstByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name=:name AND c.parentCategory.name=:parentCategoryName")
    Optional<Category> findFirstByNameAndParent(@Param("name") String name,
                                                @Param("parentCategoryName") String parentCategoryName);

}
