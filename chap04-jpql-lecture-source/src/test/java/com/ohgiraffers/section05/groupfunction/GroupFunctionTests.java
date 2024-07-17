package com.ohgiraffers.section05.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupFunctionTests {
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
     * 그룹함수
     * -> JPQL에서 사용하는 그룹함수랑 기존 SQL에서 사용하는 그룹함수는 차이가 거의 없다.
     * COUNT, SUM, AVG, MAX, MIN
     *
     * 주의사항
     * 1. 그룹함수의 반환 타입은 결과값이 정수면 Long, 실수면 Double이 반환된다.
     * 2. 값이 없는 상태에서 COUNT를 제외한 그룹함수는 null이 되고 COUNT는 0이 된다.
     *    -> null로 받은 상태에서는 long 또는 double 같은 기본자료형으로 받게되면 언박싱 과정에서 NPE가 발생한다.
     * 3. 그룹함수의 반환 자료형은 Long or Double형이기 때문에
     *    Having절에서 그룹함수 결과값과 비교하기 위한 파라미터 타입은 Long or Double로 해야한다.
     * => 받을때 래퍼 타입을 써라.
     */

    @Test
    @DisplayName("특정 카테고리의 등록된 메뉴 수 조회")
    void test1() {

        // given
        int categoryCodeParameter = 2;

        // when
        String jpql = """
                select
                count(m.menuPrice)
                from menu_section05 m
                where m.categoryCode = :categoryCode
                """;
        long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();

        // then
        assertTrue(countOfMenu >= 0);
        System.out.println("countOfMenu = " + countOfMenu);
    }

    @Test
    @DisplayName("count를 제외한 다른 그룹함수의 조회결과가 없는 경우 테스트")
    void test2() {

        // given
        int categoryCodeParameter = 2;

        // when
        String jpql = """
                select
                sum(m.menuPrice)
                from menu_section05 m
                where m.categoryCode = :categoryCode
                """;

        // then
        assertThrows(NullPointerException.class, () -> {
            long sumOfMenu = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();
        });

        assertDoesNotThrow(() -> {
            long sumOfMenu = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();
        });
    }

    @Test
    @DisplayName("groupby절과 having절을 사용한 조회 테스트")
    void test3() {

        // given
        long minPrice = 50000L;
        // 그룹함수의 반환타입은 long이므로 비교를 위한 파라미터도 long타입을 사용해야한다.

        // when
        String jpql = """
                select
                m.categoryCode, sum(m.menuPrice)
                from menu_section05 m
                group by m.categoryCode
                having sum(m.menuPrice) >= :minPrice
                """;
        List<Object[]> sumPriceOfCategoryList = entityManager.createQuery(jpql, Object[].class)
                .setParameter("minPrice", minPrice)
                .getResultList();

        // then
        assertNotNull(sumPriceOfCategoryList);
        sumPriceOfCategoryList.forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);
        });
    }
}
