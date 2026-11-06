package com.bear.section03.bidirection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiDirectionTests {

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

    //양방향은 잘 안씀.
    //이유?

    @Test
    public void 양방향_연관관계_매핑_조회_테스트() {

        // given
        int menuCode = 10;
        int categoryCode = 10;

        Menu foundMenu = entityManager.find(Menu.class, menuCode); // eager
        Category foundCategory = entityManager.find(Category.class, categoryCode); //lazy
        //양방향이라 tostring을 켜놓으면 서로 보여주려고 하다보니까 stackof??가 나옴.

        // then
        /*
            양쪽 엔티티의 toString()이 서로 연관객체를 계속 출력해 재귀호출로 StackOverFlowError가 발생.
            둘 중 하나에 toString.exclude를 적용해서 연관필드 제거해야함.
         */
        assertEquals(menuCode, foundMenu.getMenuCode());
        assertEquals(categoryCode, foundCategory.getCategoryCode());

        System.out.println(foundMenu);
        System.out.println(foundCategory);

        System.out.println("접근 전");
        foundCategory.getMenuList().forEach(System.out::println);
        System.out.println("접근 후");
    }


    @Test
    public void 양방향_연관관계_주인_객체를_이용한_삽입_테스트() {

        // given
        Menu menu = new Menu();

        menu.setMenuCode(125);
        menu.setMenuName("연관관계주인메뉴");
        menu.setMenuPrice(10000);
        menu.setOrderableStatus("Y"); //새 객체 만듦.

        menu.setCategory(entityManager.find(Category.class, 4)); //카테고리. 자식은 기존에 있던 id4번을 가지고 옴. 부모가 새 객체로 만들어졌으니, 자식

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(menu);

        entityTransaction.commit();

        // then
        Menu foundMenu = entityManager.find(Menu.class, menu.getMenuCode());
        assertEquals(menu.getMenuCode(), foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    @Test
    public void 양방향_연관관계_주인이_아닌_객체를_이용한_삽입_테스트() {

        /*
            주인이 아닌 객체는 새객체 만들어 삽입 -> 바로 넣느느 것이 아니라, 트렌젝션 시작 후에 넣어준다. 왜??
         */

        // given
        Category category = new Category();
        category.setCategoryCode(1004);
        category.setCategoryName("양방향카테고리");
        category.setRefCategoryCode(null);

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(category);
        // 카테고리 코드는 메뉴와 연관관계가 없으니까 정상적으로 동작.
        // -> fk를 관리하지 않기 때문에 카테고리를 추가해도 menu 자체는 변하지 않음. -> fk가 없으니까.
        entityTransaction.commit();

        // then
        Category foundCategory = entityManager.find(Category.class, category.getCategoryCode());
        assertEquals(category.getCategoryCode(), foundCategory.getCategoryCode());
        System.out.println(foundCategory);

    }

}
