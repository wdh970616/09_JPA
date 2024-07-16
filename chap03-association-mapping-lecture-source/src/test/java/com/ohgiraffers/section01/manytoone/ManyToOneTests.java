package com.ohgiraffers.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManyToOneTests {

    /*
     * Association Mapping은 Entity 클래스 간의 관계를 매핑하는 것을 의미한다.
     * 객체를 이용해 데이터베이스의 테이블 간의 관계를 매핑할 수 있다.
     *
     * 다중성에 의한 분류
     * 연관관계가 있는 객체 관계에서 실제로 연관을 가지는 객체의 수에 따라 분류된다.
     * - N:1(ManyToOne) 연관관계
     * - 1:N(OneToMany) 연관관계
     * - 1:1(OneToOne) 연관관계
     * - N:M(ManyToMany) 연관관계
     *
     * 방향에 따른 분류
     * 테이블의 연관관계는 외래키를 이용하여 양방향 연관관계의 특징을 가진다.
     * 참조에 의한 객체의 연관관계는 단방향이다.
     * 객체간의 연관관계를 양방향으로 만들고 싶을 경우 반대쪽에서도 필드를 추가하여 참조를 보관할 수 있다.
     * 엄밀히 말하면, 이는 양방향 관계가 아닌 단방향 2개로 볼 수 있다.
     * - 단방향 연관관계
     * - 양방향 연관관계
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

    @DisplayName("다대일 연관관계 객체 그래프 탐색을 이용한 조회 테스트")
    @Test
    public void test1() {

        // given
        int menuCode = 15;

        // when
        // 조회시 조인 구문이 실행되며 연관 테이블을 함께 조회해온다.
        MenuAndCategory foundMenu = entityManager.find(MenuAndCategory.class, menuCode);
        Category menuCategory = foundMenu.getCategory();

        // then
        assertNotNull(menuCategory);
        System.out.println("menuCategory = " + menuCategory);
    }

    @DisplayName("다대일 연관관계 객체지향쿼리(JPQL) 사용한 카테고리 이름 조회 테스트")
    @Test
    public void test2() {

        // given
        String jpql = "select c.categoryName from menu_and_category m join m.category c where m.menuCode = 15";

        // when
        // 조회시 조인 구문이 실행되며 연관 테이블을 함께 조회
        String categoryName = entityManager.createQuery(jpql, String.class).getSingleResult();

        // then
        assertNotNull(categoryName);
        System.out.println("category = " + categoryName);
    }

    /*
     * commit()을 할 경우에는 컨텍스트 내에 저장된 영속성 객체를 insert 하는 쿼리문이 작성된다.
     * 단, 카테고리는 컨텍스트 내에 존재하지 않기 때문에 카테고리 코드를 참조하는 tbl_menu에 데이터를 넣을 수 없게 된다.
     * 이때 사용할 수 있는것이 @ManyToOne 어노테이션에 영속성 전이 설정을 해주는것이다. (cascade = CascadeType.PERSIST)
     * 영속성 전이 : 특정 엔티티를 영속화 할때 연관된 엔티티도 함께 영속화를 진행한다는 의미
     */
    @DisplayName("다대일 연관관계 객체 삽입 테스트")
    @Test
    public void test3() {

        // given
        MenuAndCategory menuAndCategory = new MenuAndCategory();
        menuAndCategory.setMenuCode(9999);
        menuAndCategory.setMenuName("다대일돈까스스파게티");
        menuAndCategory.setMenuPrice(15000);

        Category category = new Category();
        category.setCategoryCode(3333);
        category.setCategoryName("다대일카테고리");
        category.setRefCategoryCode(1);

        menuAndCategory.setCategory(category);
        menuAndCategory.setOrderableStatus("Y");

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(menuAndCategory);
        transaction.commit();

        // then
        MenuAndCategory foundMenuAndCategory = entityManager.find(MenuAndCategory.class, 9999);
        assertEquals(9999, foundMenuAndCategory.getMenuCode());
        assertEquals(3333, foundMenuAndCategory.getCategory().getCategoryCode());
    }
}
