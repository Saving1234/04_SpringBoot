package com.bear.section03.field;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name="member_section03")  //entity이름. 테이블과 매핑.
    @Table(name="tbl_member_section03")
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Access(AccessType.FIELD) //기본값이 필드라서 안써도 상관이 없음. 필드를 통해 접근.
    //@Access(AccessType.PROPERTY) // 엔티티 값을 게터/세터를 통해 접근한다. 기본값 필드

    public class Member {

        /*\
            SEQUENCE: DB에 있는 시퀀스 객체 (ORACLE, PostgreSQL)
            Table : 키생성 전용 테이블 -> 기본 키값을 관리.
            AUTO : DB에 맞는 전략을 자동으로 선택
         */

        @Id
        @Column(name="member_no")
        // 테이블 만들어서 하다보니, 용량이 낭비될 수 있음. 어디에 종속이 안되는지는?
        // ->   (IDENTITY 활용 :MYSQL : 내부에 있는 COUNT 사용. -> IDENTITY를 활용하면 )
        //Access : jpa가 엔티티의 값을 어디를 통해 쓸지.
        //@Access(AccessType.PROPERTY)  //GETTER를 이용한다는 것.
        private int memberNo;

        @Column(name="member_id")
        private String memberId;

        @Column(name="member_pwd")
        private String memberPwd;

        @Column(name="nickname")
        private String nickname;

        @Column(name="phone")
        private String phone;

        @Column(name="email")
        private String email;

        @Column(name="address")
        private String address;

        @Column(name="enroll_date")
        private Date enrollDate;

        @Column(name="member_role")
        private String memberRole;

        @Column(name="status")
        private String status;

    public int getMemberNo() {
        return  memberNo;
    }

    public String getMemberId() {
        System.out.println("ㅎGETTER에서 동작함");
        return  memberId;
    }

    public String getMemberPwd() {
        System.out.println("ㅎGETTER에서 동작함");
        return  memberPwd;
    }

    public String getNickname() {
        System.out.println("ㅎGETTER에서 동작함");
        return nickname;
    }
}


