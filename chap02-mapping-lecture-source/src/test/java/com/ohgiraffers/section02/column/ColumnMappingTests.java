package com.ohgiraffers.section02.column;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnMappingTests {

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

    @DisplayName("컬럼에서 사용하는 속성 테스트")
    @Test
    public void columnMappingTest() {

        // given
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
//        Member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");
        member.setEnrollDate(LocalDate.now());
        member.setMemberRole("ROLE_MEMBER");
        member.setStatus("Y");

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.persist(member);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw new RuntimeException(e);
        }

        //then
        Member foundMember = entityManager.find(Member.class, member.getMemberNo());
        assertEquals(member.getMemberNo(), foundMember.getMemberNo());
    }
}
