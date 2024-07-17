package com.ohgiraffers.section03.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectionTests {
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
     * 프로젝션
     * select 절에 조회할 대상을 지정하는 것을 프로젝션 이라고한다.
     * (select {프로젝션 대상} from ...)
     *
     * 1. 엔티티 프로젝션
     *    - 원하는 객체를 바로 조회할 수 있다.
     *    - 조회된 엔티티는 영속성 컨텍스트가 관리한다.
     *
     * 2. 임베디드 타입 프로젝션
     *    - 엔티티와 거의 비슷하게 사용된다. (하지만 조회의 시작점이 될 수 없다. -> from절에 사용 불가)
     *    - 임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.
     *
     * 3. 스칼라 타입 프로젝션
     *    - 숫자, 문자, 날짜 같은 기본 데이터 타입
     *    - 스칼라 타입은 영속성 컨텍스트에서 관리되지 않는다.
     *
     * 4. new 명령어를 활용한 프로젝션
     *    - 다양한 종류의 단순값들을 DTO로 조회하는 방식 (new 패키지명.DTO명)
     *    -> new를 사용해서 만들기 때문에 기본생성자 필요.
     *    - new 명령어를 사용한 클래스는 엔티티가 아니므로 영속성 컨텍스트에서 관리되지 않음.
     */

    @Test
    @DisplayName("단일 엔티티 프로젝션 테스트")
    void test1() {
        // given
        // when
        String jpql = "select m from menu_section03 m";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            menuList.get(15).setMenuName("너로정했다");
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("양방향 연관관계 엔티티 프로젝션 테스트")
    void test2() {

        // given
        int menuCodeParameter = 3;

        // when
        String jpql = "select m.category from bidirection_menu m where m.menuCode = :menuCode";
        BiDirectionCategory category = entityManager.createQuery(jpql, BiDirectionCategory.class)
                .setParameter("menuCode", menuCodeParameter).getSingleResult();

        // then
        assertNotNull(category);
        System.out.println("category = " + category);

        assertNotNull(category.getMenuList());
        category.getMenuList().forEach(System.out::println);
    }

    @Test
    @DisplayName("임베디드 타입 프로젝션 테스트")
    void test3() {
        // given
        // when
        String jpql = "select m.menuInfo from embedded_menu m";
        List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();

        // then
        assertNotNull(menuInfoList);
        menuInfoList.forEach(System.out::println);
    }

    @Test
    @DisplayName("TypedQuery를 이용한 스칼라 타입 프로젝션 테스트")
    void test4() {
        // given
        // when
        String jpql = "select c.categoryName from category_section03 c";
        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();

        // then
        assertNotNull(categoryNameList);
        categoryNameList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Query를 이용한 스칼라 타입 프로젝션 테스트")
    void test5() {
        // given
        // when
        String jpql = "select c.categoryName, c.categoryCode from category_section03 c";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();

        // then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);
        });
    }

    @Test
    @DisplayName("new 명령어를 활용한 프로젝션 테스트")
    void test6() {
        // given
        // when
        String jpql = """
                select
                new com.ohgiraffers.section03.projection.CategoryInfo(c.categoryCode, c.categoryName)
                from
                category_section03 c
                """;
        List<CategoryInfo> categoryInfoList = entityManager.createQuery(jpql, CategoryInfo.class).getResultList();

        // then
        assertNotNull(categoryInfoList);
        categoryInfoList.forEach(System.out::println);
    }
}
