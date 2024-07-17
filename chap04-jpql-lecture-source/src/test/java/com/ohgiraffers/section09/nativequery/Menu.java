package com.ohgiraffers.section09.nativequery;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "menu_section09")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "section09.Menu.findByMenuCode",
                query = """
                        select *
                        from tbl_menu
                        where menu_code = ?
                        """,
                resultClass = Menu.class
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
