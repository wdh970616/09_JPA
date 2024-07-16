package com.ohgiraffers.section03.bidirection;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/*
 * 양방향 매핑에서 어느 한족이 연관관계가 주입이 되면 주인이 아닌쪽에는 속성을 지정해주어야한다.
 * 이 때, 연관관계의 주인이 아닌 객체에 mappedBy를 사용해서 연관관계의 주인 객체의 필드명을 매핑시켜놓으면 양방향 관계를 적용할 수 있다.
 */
@Entity(name = "bidirection_category")
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;
}
