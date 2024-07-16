package com.ohgiraffers.section06.compositekey.subsection02.IdClassTests;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MemberPK {

    private int memberNo;
    private String memberId;

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemberPK memberPK = (MemberPK) obj;
        return memberNo == memberPK.memberNo && Objects.equals(memberId, memberPK.memberId);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(memberNo, memberId);
    }
}
