package com.bear.section01.simple;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleJPQLTests {

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
    public void TypedQuery를_이용한_단일메뉴_조회_테스트() {

        // when

        //
        /*
            JPQL
            - SELECT, UPDATE, DELETE의 사용은 SQL과 동일
            - INSERT -> persist() 영속 메스드를 사용해야함
            - 엔티티의 별칭을 필수로 사용

            쿼리객체 : 쿼리 실행하고 결과를 전달.
            - TypedQuery : 반환할 타입을 명확하게 지정하는 방식
            - Query  : 반환할 타입을 지정할 수 없을 때 Object형으로 받는 방식.
            - getSingleList() : 결과가 정확히 한 행일 경우 사용(많으면 예외 발생)
            - getResultList() :  결과가 두 행 이상일 때 사용안하면 빈 컬렉션 반환.

            필드명 객체명.
         */
         //쿼리 쓸 때는 필드명, 객체명. 인텔리제이가 색깔 바꿔줌.
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);

        String resultMenuName = query.getSingleResult();

        assertEquals("민트미역국", resultMenuName);

    }

    @Test
    public void Query를_이용한_단일메뉴_조회_테스트() {

        // given
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";

        // when
        Query query = entityManager.createQuery(jpql);
        Object resultMenuName = query.getSingleResult(); //문자열로 받을 수 있다. 쿼리는.

        // then
        assertTrue(resultMenuName instanceof String);
        assertEquals("민트미역국", resultMenuName);

    }

    @Test
    public void TypedQuery를_이용한_단일행_조회_테스트() { //타입은 리스트임.

        // when
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.menuCode = 7";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        Menu foundMenu = query.getSingleResult();

        // then
        assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);

    }

    @Test
    public void TypedQuery를_이용한_다중행_조회_테스트() {

        // when
        String jpql = "SELECT m FROM menu_section01 as m";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);    // 반환 타입을 row와 매핑할 엔티티 타입으로 설정

        List<Menu> foundMenuList = query.getResultList(); //다중행일 때는 리스트로 받아야 여러 행을 받을 수 있겠지?

        // then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);

    }

    @Test
    public void Query를_이용한_다중행_조회_테스트() {

        // when
        String jpql = "SELECT m FROM menu_section01 as m";
        Query query = entityManager.createQuery(jpql);

        List<Menu> foundMenuList = query.getResultList();  //쿼리에서도 리스트를 넣을 수 있네??

        // then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);

    }

//연산자는 동일.
    @Test
    public void distinct를_활용한_중복제거_여러_행_조회_테스트() {

        // when
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 m";  //카테고리코드가 int니까 Integer 타입을 가져올 것.
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryCodeList = query.getResultList();

        // then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);

    }

    @Test
    public void in_연산자를_활용한_조회_테스트() {

        // when
        String jpql = "SELECT m FROM menu_section01 m WHERE m.categoryCode IN (6, 10)";  //6~10에 해당하는 행. 다중행.
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void like_연산자를_활용한_조회_테스트() {

        // when
        String jpql = "SELECT m FROM menu_section01 m WHERE m.menuName LIKE '%마늘%'";  //중간에 마늘이 들어간 행. 마늘% 마늘이 앞에 있는. %마늘 마늘이 뒤에있는.
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

}
