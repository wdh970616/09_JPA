package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedKeyTests {
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
     * 복합키가 존재하는 테이블의 매핑
     * 1. Embedded
     *    - @Embeddable 클래스에 복합키를 정의하고 엔티티에 @EmbeddedId를 이용하여 매핑
     *    - 복합키의 일부 필드만을 매핑할 수 있다. 따라서 필드수가 많은 경우 유연하게 매핑 가능하다.
     * 2. IdClass
     *    - 복합키를 필드로 정의한 클래스를 이용해서 클래스에 @IdClass를 매핑
     *    - 복합키를 구성하는 모든 필드를 한번에 매핑 가능하다.
     *    - 코드가 간결하다.
     *
     * @EmbeddedId가 조금더 객체지향적인 방식이고, IdClass가 관계형 데이터베이스에 좀더 가까운 방식이다.
     * 두 방식 모두 복합키 클래스를 영속성 컨텍스트가 관리하지 않는다는 특징을 가진다.
     *
     * 주의할 점
     * 1. 복합키의 매핑에서 복합키 클래스에 equals, hashCode 메소드를 구현해야한다.
     *    -> JPA 엔티티 객체의 동일성을 판단하기 위해 필요
     * 2. @GeneratedValue 어노테이션을 사용해서 복합키를 자동으로 생성하는 것은 불가능하다.
     */

    @DisplayName("임베디드 아이디 사용한 복합키 테이블 매핑 테스트")
    @Test
    public void embeddedIdTest() {

        // given
        Member member = new Member();
        member.setMemberPK(new MemberPK(1, "user01"));
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 삼성동");

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(member);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException();
        }

        // then
        Member foundMember = entityManager.find(Member.class, member.getMemberPK());
        assertEquals(member.getMemberPK(), foundMember.getMemberPK());
    }
}
