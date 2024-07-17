package com.ohgiraffers.section08.namedquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class JpqlNamedQueryTests {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @Test
    @DisplayName("@NamedQuery - findAll")
    void test1() {
        // given
        // when
        List<Menu> menuList = entityManager
                .createNamedQuery("section08.namedquery.Menu.findAll", Menu.class).getResultList();

        // then
        assertThat(menuList).isNotNull(); // assertNotNull(menuList); 와 동일
        menuList.forEach(System.out::println);
    }

    // findMenuByName -> 메뉴 이름을 파라미터로 받고, 일치하는 메뉴객체를 반환하는 JPQL
    @Test
    @DisplayName("@NamedQuery - findMenuByName")
    void test2() {

        // given
        String menuNameParameter = "우럭스무디";

        // when
        Menu foundMenu = entityManager.createNamedQuery("section08.namedquery.Menu.findMenuByName", Menu.class)
                .setParameter("menuNameParameter", menuNameParameter)
                .getSingleResult();

        // then
        assertEquals(menuNameParameter, foundMenu.getMenuName());
        System.out.println("foundMenu = " + foundMenu);
    }

    // 파라미터가 메뉴명으로 들어간 메뉴를 전부 조회하는 JPQL
    @Test
    @DisplayName("@NamedQuery - findMenuByParameter")
    void test3() {
        // given
        String parameter = "밥";

        // when
        List<Menu> bobMenuList = entityManager.createNamedQuery("section08.namedquery.Menu.findMenuByParameter", Menu.class)
                .setParameter("parameter", parameter)
                .getResultList();

        // then
        assertNotNull(bobMenuList);
        bobMenuList.forEach(System.out::println);
    }
}
