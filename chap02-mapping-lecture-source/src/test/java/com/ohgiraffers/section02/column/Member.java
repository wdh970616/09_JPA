package com.ohgiraffers.section02.column;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


/*
 * 컬럼 매핑 시 @Column 어노테이션에 사용 가능한 속성들
 * 1. name : 매핑할 테이블의 컬럼 이름
 * 2. insertable : 엔티티 저장 시 필드 저장 여부 (default : true)
 * 3. updatable : 엔티티 수정 시 필드 수정 여부 (default : true)
 *
 * 4. table : 엔티티와 매핑될 테이블 이름. 하나의 엔티티를 두 개 이상의 테이블에 매핑할 때 사용. (@SecondaryTable 사용)
 * 5. nullable : null값 허용 여부 설정. not null 제약조건에 해당함 (true 기본값)
 * 6. unique : 컬럼의 유일성 제약 조건
 * (두 개 이상 컬럼에 unique 제약조건을 설정하기 위해서는 클래스 레벨에서 @Table의 uniqueConstraints 속성에 설정)
 * 7. columnDefinition : 직접 컬럼의 DDL을 지정
 * 8. length : 문자열 길이. String 타입에서만 사용. (default : 255)
 */

// @Entity(name="member_section02")
@Table(name = "tbl_member_section02")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd", length = 100)
    private String memberPwd;

    @Column(name = "nickname")
    @Transient // 테이블 생성시 무시
    private String nickname;

    // columnDefinition
    @Column(name = "phone", columnDefinition = "varchar(200) default '010-0000-0000'")
    private String phone = "010-0000-0000";

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "status")
    private String status;
}
