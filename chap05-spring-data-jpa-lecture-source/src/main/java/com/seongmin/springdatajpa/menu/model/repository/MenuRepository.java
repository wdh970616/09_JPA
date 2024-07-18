package com.seongmin.springdatajpa.menu.model.repository;

import com.seongmin.springdatajpa.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// EntityManagerFactory, EntityManager, EntityTransaction -> 자동구현
// JpaRepository<엔티티명, PK타입>
public interface MenuRepository extends JpaRepository<Menu, Integer> {

//    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort menuPrice1);
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);
    List<Menu> findByMenuPriceEquals(Integer menuPrice);
    List<Menu> findByMenuPriceGreaterThanEqualOrderByMenuPrice(Integer menuPrice);

}
