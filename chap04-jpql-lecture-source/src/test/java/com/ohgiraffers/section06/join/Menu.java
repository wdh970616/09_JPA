package com.ohgiraffers.section06.join;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "menu_section06")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
