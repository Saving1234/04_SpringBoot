package com.bear.section03.field;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiledAcessTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }
    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }
    @AfterEach
    public void closeManager() {
        entityManager.close();
    }


    @Test
    public void 필드_접근_테스트() {

        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");

        entityManager.persist(member);

        Member foundMember = entityManager.find(Member.class, 1);
        //클래스 이름을 직접 표시. replection : 실행 중 클래스 정보를 확인하고 필드나 메서드에 접근하는 기능. -> 라이브러리, 프레임워크에서 쓰임.
        //private 필드의 정보도 가져올 수 있음. jpa -> 안에 있는 정보를 가지고 옴.

        assertEquals(member, foundMember);
//        System.out.println(foundMember);

    }


}
