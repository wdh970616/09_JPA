package com.seongmin.springdatajpa.menu.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuDto {

    private int menuCode;
    private int categoryCode;
    private String MenuName;
    private int menuPrice;
    private String orderableStatus;
}
