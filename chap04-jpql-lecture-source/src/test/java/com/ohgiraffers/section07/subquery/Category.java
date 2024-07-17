package com.ohgiraffers.section07.subquery;

import com.ohgiraffers.section06.join.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "category_section07")
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    // 순환참조 방지
    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
