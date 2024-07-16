package com.ohgiraffers.section01.simple;


import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleJPQLTests {

    /*
     * JPQL(Java Persistence Query Language)
     * 엔티티 객체를 중심으로 개발할수있는 객체지향쿼리
     * SQL보다 간결하고 특정 DBMS에 의존하지 않는다.
     * 방언을 통해 해당 DBMS에 맞는 SQL을 실행하게된다.
     * JPQL은 find() 메소드를 통한 조회와 다르게 항상 데이터베이스에서 SQL을 실행해서 결과를 조회한다.
     */

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
     * JPQL의 기본 문법
     * SELECT, UPDATE, DELETE 등의 키워드 사용은 SQL과 동일하다.
     * INSERT 는 persist() 메소드를 사용하면 된다.
     * 키워드는 대소문자를 구분하지 않지만, 엔터티와 속성은 대소문자를 구분함에 유의한다.
     * 엔터티 별칭을 필수로 사용해야 하며 별칭 없이 작성하면 에러가 발생한다.
     */

    /*
    JPQL 사용 방법은 다음과 같다.
    1. 작성한 JPQL(문자열)을 `entityManager.createQuery()` 메소드를 통해 쿼리 객체로 만든다.
    2. 쿼리 객체는 `TypedQuery`, `Query` 두 가지가 있다.
      2-1. TypedQuery : 반환할 타입을 명확하게 지정하는 방식일 때 사용하며 쿼리 객체의 메소드 실행 결과로 지정한 타입이 반환 된다.
      2-2. Query : 반환할 타입을 명확하게 지정할 수 없을 때 사용하며 쿼리 객체 메소드의 실행 결과로 Object 또는 Object[]이 반환 된다.
    3. 쿼리 객체에서 제공하는 메소드 `getSingleResult()` 또는 `getResultList()`를 호출해서 쿼리를 실행하고 데이터베이스를 조회한다.
      3-1. getSingleResult() : 결과가 정확히 한 행일경우 사용하며 없거나 많으면 예외가 발생한다.
      3-2. getResultList() : 결과가 2행 이상일 경우 사용하며 컬렉션을 반환한다. 결과가 없으면 빈 컬렉션을 반환한다.
    */

    @Test
    @DisplayName("TypedQuery를 이용한 단일메뉴 조회 테스트")
    public void test1() {
        // given
        // when
        String jpql = "select m.menuName from menu_section01 m where m.menuCode = 7";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class); // 반환결과를 문자열로 받겠다는 의미
        String resultMenuName = query.getSingleResult();

        // then
        assertEquals("민트미역국", resultMenuName);
    }

    @Test
    @DisplayName("Query를 이용한 단일메뉴 조회 테스트")
    void test2() {
        // given
        // when
        String jpql = "select m.menuName from menu_section01 m where m.menuCode = 7";
        Query query = entityManager.createQuery(jpql);
        Object singleResult = query.getSingleResult();

        // then
        assertTrue(singleResult instanceof String);
        assertEquals("민트미역국", singleResult);
    }

    @Test
    @DisplayName("TypedQuery를 이용한 단일행 조회 테스트")
    public void test3() {
        // given
        // when
        String jpql = "select m from menu_section01 m where m.menuCode = 7";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu resultMenu = query.getSingleResult();

        // then
        System.out.println("resultMenu = " + resultMenu);
        assertEquals("민트미역국", resultMenu.getMenuName());
    }

    @Test
    @DisplayName("TypedQuery를 이용한 다중행 조회 테스트")
    public void test4() {
        // given
        // when
        String jpql = "select m from menu_section01 m";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> menuList = query.getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Query를 이용한 다중행 조회 테스트")
    public void test5() {
        // given
        // when
        String jpql = "select m from menu_section01 m";
        Query query = entityManager.createQuery(jpql);
        List<Menu> menuList = query.getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    @DisplayName("distinct를 사용한 중복제거 여러행 조회 테스트")
    void test6() {
        // given
        // when
        String jpql = "select distinct m.categoryCode from menu_section01 m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryList = query.getResultList();

        // then
        assertNotNull(categoryList);
        categoryList.forEach(System.out::println);
    }

    @Test
    @DisplayName("in 연산자를 활용한 조회 테스트")
    void test7() {
        // given
        // when
        String jpql = "select m from menu_section01 m where m.categoryCode in(6,7) order by m.menuCode desc";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    @DisplayName("like 연산자를 활용한 조회 테스트")
    void test8() {
        // given
        // when
        String jpql = "select m from menu_section01 m where m.menuName like '%마늘%'";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
