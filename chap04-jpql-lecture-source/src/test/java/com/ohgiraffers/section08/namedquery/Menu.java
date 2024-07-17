package com.ohgiraffers.section08.namedquery;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "menu_section08")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@NamedQueries({ // 정적 쿼리를 정의하는데에 사용되는 어노테이션
        @NamedQuery( // JPQL을 미리 엔티티 클래스에 정의를 해둠으로써 재사용성을 높일 수 있다.
                name = "section08.namedquery.Menu.findAll", // 쿼리 이름
                query = "select m from menu_section08 m"    // 쿼리 정의
        ),
        @NamedQuery(
                name = "section08.namedquery.Menu.findMenuByName",
                query = "select m from menu_section08 m where menuName = :menuNameParameter"
        ),
        @NamedQuery(
                name = "section08.namedquery.Menu.findMenuByParameter",
                query = "select m from menu_section08 m where menuName like concat('%', :parameter, '%')"
        )
})
public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
