package com.ohgiraffers.section06.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JoinTests {
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

    /*
     * 조인의 종류
     * 1. 일반 조인 : SQL 조인들을 의미
     * 2. 패치 조인 : JPQL에서 성능 최적화를 위해서 제공하는 기능 : 연관된 엔티티나 컬렉션을 한번에 조회 -> 즉시로딩 수행(join fetch)
     */

    @Test
    @DisplayName("내부조인을 이용한 조회 테스트")
    void test1() {
        // given
        // when
//        <N + 1 문제>
//        String jpql = """
//                select
//                m
//                from
//                menu_section06 m join m.category c
//                """;
        String jpql = """
                select
                m
                from
                menu_section06 m join fetch m.category c
                """;
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    @DisplayName("외부조인을 위한 조회 테스트")
    void test2() {
        // given
        // when
        String jpql = """
                select
                m.menuName, c.categoryName
                from
                menu_section06 m right join m.category c
                order by m.category.categoryCode
                """;
        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    @DisplayName("컬렉션 조인을 이용한 조인 테스트")
    void test3() {
        // 컬렉션 조인 : 컬렉션을 지니고있는 엔티티를 기준으로 조인하는것을 의미
        // given
        // when
        String jpql = """
                select
                c.categoruName, m.menuName
                from category_section06 c left join c. menuList m
                """;
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        // then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }
}
