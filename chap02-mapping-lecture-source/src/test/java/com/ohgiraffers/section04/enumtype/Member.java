package com.ohgiraffers.section04.enumtype;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


// @Entity(name="member_section04")
@Table(name="tbl_member_section04")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    @Column(name ="member_id")
    private String memberId;

    @Column(name ="member_pwd")
    private String memberPwd;

    @Column(name ="nickname")
    private String nickname;

    @Column(name ="phone")
    private String phone;

    @Column(name ="email")
    private String email;

    @Column(name ="address")
    private String address;

    @Column(name ="enroll_date")
    private LocalDate enrollDate;

    @Column(name ="member_role")
    @Enumerated(EnumType.STRING)
    private RoleType memberRole;

    @Column(name ="status")
    private String status;
}
