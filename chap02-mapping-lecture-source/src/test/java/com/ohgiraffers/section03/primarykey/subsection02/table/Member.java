package com.ohgiraffers.section03.primarykey.subsection02.table;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


// @Entity(name="member_section03_sub02")
@Table(name = "tbl_member_section03_sub02")
@TableGenerator(
        name = "member_seq_table_generator",
        table = "tbl_my_sequence",
        pkColumnValue = "my_seq_member_no"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "member_seq_table_generator")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "status")
    private String status;
}
