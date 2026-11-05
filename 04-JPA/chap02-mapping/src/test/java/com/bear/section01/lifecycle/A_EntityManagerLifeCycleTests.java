package com.bear.section01.lifecycle;


import com.bear.section01.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.*;

public class A_EntityManagerLifeCycleTests {

    //persistance : 영속성 타입으로 넣은 것.
    private static EntityManagerFactory entityManagerFactory; //싱글톤 패턴 : 한개 만들고 계속 재사용하겠다. 그래서 static 씀. 주소값 바뀌지 않음(재사용!)
    private EntityManager entityManager; //컨텍스트 고나리.  이건 주소값 바뀜.


    //메모리에 담을 상자를 만들어야 메모리에 담김.
    //안만들면 당연히 날라감.

    //테스트코드.
    /*
        세가지로 나눠서 작성.
        1. given : 테스트를 진행하기위해 준비작업 (전제조건)
        2. when : 언제 동작시킬건지 정해주기. (테스트의 핵심로직 수행./ 행동 또는 메서드)
        3. then : 테스트의 결과를 검증.
     */
    @BeforeAll
    // 전체테스트가 실행되기 전 딱 한번만 실행됨.
    // 싱글턴 패턴을 적용하거나, 리소스가 큰 작업을 수행할 때 사용.
    public static void initFactory(){
        //1. persistence.xml파일 : 설정파일을 읽어 데이터베이스 연결 정보 및 엔티티 클래스 목록 파악.
        //2. 메타 데이터 처리 : 엔티티 클래스 구조랑, 관계를 정보 저장.
        //3. 커넥션 풀 초기화 : 데이터베이스 연결을 위해 내부커넥션 풀을 설정 및 초기화.
        //4. 캐시 초기화 : 성능 최적화를 위한 (1차 캐시, 쿼리 캐시) 것들을 초기화.
        // 즉, 시작할 수 있도록 준비.
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");

    }
    //테스트 매서드 호출 되기 전에,  동작.
    @BeforeEach
    public void initManager(){

        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory(){  //factory가 static이라.
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager(){//manager가 nonstatic이라.
        entityManager.close();
    }

    @Test
    public void 테이블_만들기_테스트() {
        // Given
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("user01");
        member.setNickname("유저");

        // When
        entityManager.persist(member); // 영속 상태
        Member foundMember = entityManager.find(Member.class, member.getMemberNo());
        System.out.println("foundMember = " + foundMember);

        // Then
    }



}
