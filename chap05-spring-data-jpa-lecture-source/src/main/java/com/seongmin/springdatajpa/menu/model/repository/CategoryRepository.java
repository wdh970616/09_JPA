package com.seongmin.springdatajpa.menu.model.repository;

import com.seongmin.springdatajpa.menu.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // JPQL 사용
    @Query(value = "select c from Category c order by c.categoryCode")
    List<Category> findAllCategoryByJPQL();

    // NativeQuery 사용, nativeQuery 작성시에는 반드시 native=true 속성을 지정해줘야한다.
    @Query(value = "select * from tbl_category order by category_code", nativeQuery = true)
    List<Category> findAllCategoryByNativeQuery();
}
