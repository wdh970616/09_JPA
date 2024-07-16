package com.ohgiraffers.section01.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

/*
 * @Entity 어노테이션은 JPA에서 사용되는 엔티티 클래스임을 표시한다.
 * 이 어노테이션을 사용하면 해당 클래스가 데이터베이스의 테이블과 매핑된다.
 * @Entity 어노테이션은 클래스 선언 위에 위치해야 한다.
 * 또한, `name` 속성을 사용하여 엔티티 클래스와 매핑될 테이블의 이름을 지정할 수 있다.
 * 생략하면 자동으로 클래스 이름을 엔티티 명으로 사용한다.
 */


// @Entity(name="member_section01")
@Table(name = "tbl_member_section01")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    /*
     * Entity
     * - 프로젝트 내에 다른 패키지에도 동일한 엔티티가 존재할 경우 식별하기 위한 name 필수지정해야함.
     * - 기본 생성자는 필수 작성
     * - final 클래스, enum, interface, inner class 에서는 사용 할 수 없다.
     * - 저장할 필드에 final을 사용하면 안된다.
     */


    // PK 가 우선시 되어 컬럼이 생기고, 일반 컬럼은 오름차순으로 생성된다.

    @Id
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "status")
    private String status;
}
