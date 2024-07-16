package com.ohgiraffers.section03.primarykey.subsection01.identify;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

public class PrimaryKeyMappingTests {

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
       Primary key에는 @Id 어노테이션과 @GeneratedValue 어노테이션을 사용한다.
       @Id 어노테이션은 엔티티 클래스에서 primary key 역할을 하는 필드를 지정할 때 사용된다.
       @GeneratedValue 어노테이션을 함께 사용하면 primary key 값을 자동으로 생성할 수 있다.

	   데이터베이스마다 기본 키를 생성하는 방식이 서로 다르다.
	   @GeneratedValue 어노테이션은 다음과 같은 속성을 가지고 있다.

	- strategy : 자동 생성 전략을 지정
    	- GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임(MySQL의 AUTO_INCREMENT)
    	- GenerationType.SEQUENCE : 데이터베이스 시퀀스 객체 사용(ORACLE의 SEQUENCE)
    	- GenerationType.TABLE : 키 생성 테이블 사용
    	- GenerationType.AUTO : 자동 선택 (MySQL이라면 IDENTITY, ORACLE이라면 SEQUENCE로 선택)
	- generator : strategy 값을 GenerationType.TABLE로 지정한 경우 사용되는 테이블 이름을 지정
	- initialValue : strategy 값을 GenerationType.SEQUENCE로 지정한 경우 시퀀스 초기값을 지정
	- allocationSize : strategy 값을 GenerationType.SEQUENCE로 지정한 경우 시퀀스 증가치를 지정
    */

    @DisplayName("식별자 매핑 테스트")
    @Test
    public void identityMappingTest() {

        // given
        Member member1 = new Member();
        member1.setMemberId("user01");
        member1.setMemberPwd("pass01");
        member1.setNickname("홍길동");
        member1.setPhone("010-1234-5678");
        member1.setAddress("서울시 종로구");
        member1.setEnrollDate(LocalDate.now());
        member1.setMemberRole("ROLE_MEMBER");
        member1.setStatus("Y");

        Member member2 = new Member();
        member2.setMemberId("user02");
        member2.setMemberPwd("pass02");
        member2.setNickname("유관순");
        member2.setPhone("010-4321-8765");
        member2.setAddress("서울시 강남구");
        member2.setEnrollDate(LocalDate.now());
        member2.setMemberRole("ROLE_MEMBER");
        member2.setStatus("Y");

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.persist(member1);
            entityManager.persist(member2);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException();
        }

        // then

        String jpql = "select a.memberNo from member_section03_sub01 a"; // entity 명 사용, 별칭 필수

        List<Integer> members = entityManager.createQuery(jpql, Integer.class).getResultList();

//        members.forEach(System.out::println);
        for (Integer member : members) {
            System.out.println(member);
        }
    }
}
