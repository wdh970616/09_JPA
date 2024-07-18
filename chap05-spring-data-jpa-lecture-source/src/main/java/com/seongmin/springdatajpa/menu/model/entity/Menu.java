package com.seongmin.springdatajpa.menu.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "menu_name")
    private String MenuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
