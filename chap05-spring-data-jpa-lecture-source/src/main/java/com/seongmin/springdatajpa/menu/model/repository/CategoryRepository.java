package com.seongmin.springdatajpa.menu.model.repository;

import com.seongmin.springdatajpa.menu.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
