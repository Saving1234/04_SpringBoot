package com.bear.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PropertyAccessTest1 {

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
    public void 테이블만들기_테스트(){

        Member member = new Member();
        member.setMemberId("user01");
        member.setMemberNo(1);
        member.setMemberPwd("user01");
        member.setNickname("홍길동");

        //when
        entityManager.persist(member); //영속으로 만들게싿.  테이블이 없어도 ddlauto를 create로 했기 때문에 테이블 생성.

        Member foundMember = entityManager.find(Member.class, member.getMemberNo()); //생성했기에, memberNo 찾아옴. //1차 캐시에서 찾아옴


    }


}
