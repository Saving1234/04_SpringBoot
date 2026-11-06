package com.bear.section05.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JoinTests {

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

    /*
        1. 일반조인 : 일반적인 sql 조인을 의미 (내부조인, 외부조인, 컬렉션 조인, 세타 조인)
        2. 패치조인 : JPQL에서 성능 최적화를 위해 제공하는 기능 (연관되는 엔티티나 켈렉션을 한번에 조회) -> 즉시 로딩.
     */

    @Test
    public void 내부조인을_이용한_조회_테스트() {

        // when
        String jpql = "SELECT m FROM menu_section05 m JOIN m.category c"; //카테고리 코드를 각각의 엔티티에서 가져옴.
        /*
            조인을 적었지만 SELECT 대상은 menu -> 조인을 해도 category를 함께 가져오지는 않음.
            Menu는 ManyTone 사용.(즉시 로딩. Eager) -> 즉시 로딩하자는 지시가 있자 -> 카테고리 가져옴 -> 그러다보니 카터고리 조회 N번이 발생 (N+1번의 쿼리가 나감.)
            N+1 문제  찾아보기. -> 해결방법이 상황별로 다름. 비즈니스 로직 경우 필요하거나 다르게 하는 방법이 있기에.
            m.category가 manyToOne이라서 기본값이 Eager라 다 가져옴.
         */

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        // then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 페치조인을_이용한_조회_테스트() {

        //when
        String jpql = "SELECT m FROM menu_section05 m JOIN FETCH m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

    @Test
    public void 외부조인을_이용한_조회_테스트() {
        //반복되는 것도 같이 가져오겠다. Right /Left

        // when
        String jpql = "SELECT m.menuName, c.categoryName " +
                      "FROM menu_section05 m RIGHT JOIN m.category c " +
                      "ORDER BY m.category.categoryCode";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        // then
        assertNotNull(menuList);

        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 컬렉션_조인을_이용한_조회_테스트() {

        // when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section05 c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        // then
        assertNotNull(categoryList);

        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 세타조인을_이용한_조회_테스트() {

        // when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section05 c, menu_section05 m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        // then
        assertNotNull(categoryList);

        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });

    }
}
