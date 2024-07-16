package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "member_section06_sub01")
@Table(name = "tbl_member_section06_sub01")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @EmbeddedId
    private MemberPK memberPK;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;
}
