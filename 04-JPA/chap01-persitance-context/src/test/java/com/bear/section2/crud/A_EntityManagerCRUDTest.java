package com.bear.section2.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class A_EntityManagerCRUDTest {

    private static EntityManagerFactory entityManagerFactory; //싱글톤 패턴 : 한개 만들고 계속 재사용하겠다. 그래서 static 씀. 주소값 바뀌지 않음(재사용!)
    private EntityManager entityManager; //컨텍스트 고나리.  이건 주소값 바뀜.

    @BeforeAll
    public static void initFactory(){

        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");

    }

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
    public void 새로운_메뉴_추가_테스트() {

        Menu menu = new Menu();

        menu.setMenuName("스파이시 에그마요 샌드위치");
        menu.setMenuPrice(5500);
        menu.setCategoryCode(4);
        menu.setOrderableStatus("Y");

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            //Persist(): 엔터티를 영속성 컨텍스트에 등록만하고, insert구문은 commit 시점까지 쓰기 지연 저장소에 모았다가 한번 에 내보낸다.
            // 그러나, Identity 전략에 의해 컨텍스트에 저장할 id 값(pk/key)이 필요하므로 미리 알기위해 insert가 바로 실행된다.
            //(영속성 컨텍스트에 등록하기 위해 미리 보낸다)
            entityManager.persist(menu); //만든 객체를 영속성 컨텍스트에 등록. //커밋 하기 전에, sql문 만듬. -> database에 요청.
//
            entityTransaction.commit();//db에 반영. flush()
//            entityTransaction.rollback();//db에 반영. flush()
        } catch (Exception e) {

            entityTransaction.rollback();
            e.printStackTrace();
        }

        //contains : 해당 객체가 persistance context에 등록이 되어있는지 확인.
        //assert : 테스트 결과 체크할 때 쓰는 조건함수
        assertTrue(entityManager.contains(menu));  //등록되어있으니 true.

    }

    @Test
    public void 메뉴코드로_메뉴_조회_테스트() {

        int menuCode = 2;

        //find : 찾는다. jpal로 엔티티 조회. -> 컨텍스트에 등록하기 위해 데이터베이스에서 가져오는데, id값 기반으로 찾아온다.
        //(엔티티 클래스, 식별자) 식별자로 엔티티 조회.

        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //있으면 true, 없으면 false.
        //값이 있는지, 없는지 보니까.
        Assertions.assertNotNull(foundMenu);

        assertEquals(menuCode, foundMenu.getMenuCode());
        System.out.println("foundMenu = " + foundMenu);

    }

    @Test
    public void 메뉴_이름_수정_테스트() {

        //영속성 컨텍스트에 persist로 넣어져있으니, 찾아온다.
        Menu menuToChange = entityManager.find(Menu.class, 2);
        System.out.println("menu = " + menuToChange);

        String menuNameToChange = "갈치스무디";

        //트렌젝션으로 매니저를 묶음. 매니저에 있는 테이블들. tbl_menu가 묶임.
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            //set으로 바꿈. menuName 바꿈.

            menuToChange.setMenuName(menuNameToChange);
            //커밋되며 변함.
            //스냅샷에서 저장되어있던 우럭스무디에서 현재상태(entity) 갈치스무디로 변함.
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        //갈치스무디가 찾으려고 했던 메뉴네임과 같으면 true.아니면 false.
        assertEquals(menuNameToChange, entityManager.find(Menu.class, 2).getMenuName());

    }

    @Test
    public void 메뉴_삭제하기_테스트() {

        Menu menuToRemove = entityManager.find(Menu.class, 1); //컨텍스트에 menuToMove 객체 등록. find로 데이터베이스에서 찾아와서.

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {

            entityManager.remove(menuToRemove); //영속상태 해제. 찾아온 행을.
            entityTransaction.commit();//제거 구문 작성 및 전송.
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }


        Menu removedMenu = entityManager.find(Menu.class, 1);
        assertNull(removedMenu); //엔티티메니저가 찾아온 값이 없으면 true. 있으면 false.
    }



}
