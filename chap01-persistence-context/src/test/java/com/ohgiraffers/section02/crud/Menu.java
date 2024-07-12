package com.ohgiraffers.section02.crud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "select02_menu") // 엔티티 객체로 만들기 위한 어노테이션 (다른 패키지에 동일 이름의 클래스가 존재하면 name 지정 필수)
@Table(name = "tbl_menu")   // 데이터베이스에 매핑될 테이블 이름 설정
public class Menu {

    @Id // pk에 해당하는 속성
    private int menuCode;

    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;
}
