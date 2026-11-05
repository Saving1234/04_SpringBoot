package com.bear.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class Practice {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    @BeforeAll  //전체 실행 시 한번만.
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");   //엔티티 매니저 팩토리 연결하기 위한 이름.
        System.out.println("이닛 팩토리 실행 ");
    }

    @BeforeEach //테스트 실행시 마다 한번씩.
    public void initManager() {
        System.out.println("entityManagerFactory 실행 : ");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll //전체 종료 시 한번만.
    public static void closeFactory() {
        System.out.println("entityManagerFactory 실행 : AfterAll " );
        entityManagerFactory.close();
    }

    @AfterEach //전체 종료될 때마다 한번씩.
    public void closeManager() {
        System.out.println("entityManagerFactory AfterEach" );
        entityManager.close();
    }

    //영속성 컨텍스트 생명주기와 커밋시점이 동일하여, 커밋할 때 생명주기가 끝남. -> tr

  @Disabled("성공/실패를 예측한 뒤 이 애너테이션을 제거하고 실행하세요.")   //테스트 코드르르 실행시키지 않겠다.
    @Test
    void 문제01_같은_식별자로_두_번_조회하면_같은_객체일까() {
        Menu firstMenu = entityManager.find(Menu.class, 2);
        Menu secondMenu = entityManager.find(Menu.class, 2);
        assertSame(firstMenu, secondMenu);
    }

   @Disabled("성공/실패를 예측한 뒤 이 애너테이션을 제거하고 실행하세요.")
    @Test
    void 문제02_persist하면_엔티티는_영속_상태가_될까() {
        Menu newMenu = createMenu(9001, "연습 메뉴 1");
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(newMenu);
        assertTrue(entityManager.contains(newMenu));
    }

    @Disabled("성공/실패를 예측한 뒤 이 애너테이션을 제거하고 실행하세요.")
    @Test
    void 문제03_영속_엔티티의_값만_바꾸면_변경을_감지할까() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("실핼");
        Menu foundMenu = entityManager.find(Menu.class, 2);
        String changedName = "변경 감지 메뉴";

        foundMenu.setMenuName(changedName);
        entityManager.flush();
        entityManager.clear();

        assertEquals(changedName, entityManager.find(Menu.class, 2).getMenuName());
    }

    @Disabled("성공/실패를 예측한 뒤 이 애너테이션을 제거하고 실행하세요.")
    @Test
    void 문제04_remove한_엔티티를_같은_컨텍스트에서_다시_조회할_수_있을까() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Menu foundMenu = entityManager.find(Menu.class, 2);
        Menu menu = new Menu();

        menu.setMenuName("홍길동");
        menu.setMenuCode(1);
        menu.setMenuPrice(5000);
        menu.setCategoryCode(2);

        entityManager.persist(menu);
        entityManager.remove(foundMenu);

        assertNull(entityManager.find(Menu.class, 2));
    }

//    @Disabled("성공/실패를 예측한 뒤 이 애너테이션을 제거하고 실행하세요.")
    @Test
    void 문제05_merge를_호출하면_준영속_객체_자체가_다시_영속_상태가_될까() {
        Menu detachedMenu = entityManager.find(Menu.class, 2);
        entityManager.detach(detachedMenu);
        detachedMenu.setMenuName("병합 연습 메뉴");

        Menu mergedMenu = entityManager.merge(detachedMenu);

        assertTrue(entityManager.contains(detachedMenu));
//        assertTrue(entityManager.contains(mergedMenu));
    }

    private Menu createMenu(int menuCode, String menuName) {
        return new Menu(menuCode, menuName, 10000, 1, "Y");
    }
}
