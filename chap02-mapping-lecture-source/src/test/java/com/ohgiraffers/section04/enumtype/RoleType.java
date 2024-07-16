package com.ohgiraffers.section04.enumtype;

public enum RoleType {

    /*
     * @Enumerated 어노테이션은 Enum 타입 매핑을 위해 사용된다.
     * - EnumType.ORDINAL : Enum 타입을 순서로 매핑한다.
     * - EnumType.STRING : Enum 타입을 문자열로 매핑한다.
     *
     * ORDINAL
     * -> enum 객체 안에 넣어준 순서대로 사용하는 방법
     * -> 용량을 아낄 수 있다는 장점, 순서가 바뀌면, 지정된 데이터가 바뀌는 단점
     * STRING
     * -> enum 객체를 문자열로 사용하는 방법
     * -> 문자열로 지정된 키처럼 사용하는 방법
     * -> enum의 순서가 바뀌거나 enum이 추가되어도 안전하다.
     *
     */

    /*
     * Enum -> 서로 연관된 상소들의 집합을 의미하는 데이터 타입
     * 장점
     * - 코드 가독성 높이기 가능
     * - 타입 세이프티를 보장 가능
     */

    ADMIN, MEMBER
}
