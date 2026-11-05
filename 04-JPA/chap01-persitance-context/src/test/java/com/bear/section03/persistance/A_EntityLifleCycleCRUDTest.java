package com.bear.section03.persistance;

import com.bear.section2.crud.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class A_EntityLifleCycleCRUDTest {

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
    public void 준영속성_detach_테스트() {

        Menu foundMenu1 = entityManager.find(Menu.class, 11);  //find로 찾아와서 컨텍스트에 등록한 상태.
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        //준영속상태로 변경.
        //컨텍스트로 관리하던 엔티티 객체를 관리하지 않는 상태. 컨텍스트가 신경 안씀.(jpa가 신경안씀.)
        //객체를 데이터로만 쓰려고 할 때. 컨텍스트에는 남기지 않고.
        entityManager.detach(foundMenu2);

        //영속상태에서의 변경.
        foundMenu1.setMenuPrice(5000);

        //준영속 상태에서의 변경.
        foundMenu2.setMenuPrice(5000);

        //영속상태에서 바꿨으니 바뀌었음. 그래서 true
        assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());

        //준영속상태였으니 쿼리 실행되지 않았을 것. 컨텍스트에서 빠졌을테니.
        assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());
    }

    @Test
    public void 준영속성_clear_테스트() {

        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        entityManager.clear(); //둘 다 영속상태에서 해제.

        foundMenu1.setMenuPrice(5000);

        foundMenu2.setMenuPrice(5000);

        assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());

        assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());
    }

    @Test
    public void 준영속성_close_테스트() {

        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        entityManager.close(); //둘 다 닫아버림. 컨텍스트 종료. 그러니, 이것들은 평범하나 자바 객체로 변환.

        foundMenu1.setMenuPrice(5000);

        foundMenu2.setMenuPrice(5000);

        //당연히 false.
        //준영속이 되어버림. 컨텍스트 자체가 사라지니까.
        assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());

        assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());
    }


    @Test
    public void 병합_merge_수정_테스트() {

        //detachEndity에서 가져오는 것을 의미.
        //준영속 엔티티 객체의 식별자 값으로 1차 엔디티 객체 조회.
        //조회한 엔디티 객체에 준영속 상태의 엔디티 객체 값을 병합해서 엔디티 객체 반환.
        // --> 영속성 컨텍스트의 관리를 받지 않는 엔티티 값을 영속성 컨텍스트에 관리되는 엔터티에 복사하는 기능.

        Menu menuToDetach = entityManager.find(Menu.class, 2); //영속상태. new 로 heap영역에
        entityManager.detach(menuToDetach); // 준영속으로 변환.

        menuToDetach.setMenuName("수박죽"); // 바꿔줬을 때 -> 반영 안됨. 컨텍스트에서 빠졌으니.

        Menu refoundMenu = entityManager.find(Menu.class, 2); //영속 상태. heap 영역안에 넣은 값이 채워짐. 또 찾아올 때는  heap 주소값을 가지고 와서 stack에 새 객체를 만듬.

        System.out.println(menuToDetach.hashCode());//영속 주소.
        System.out.println(refoundMenu.hashCode());//준영속으로, 새 객체.

        //stack

        entityManager.merge(menuToDetach);  //준영속 객체의 값을 영속 객체로 옮기는 행위. -> 영속상태와 준영속 상태를 가지고 와 바뀐 내용을 합쳐줌.
                                            // 영속 상태에서 객체가 있으니 병합해줌.(아이디가 일치한 준영속 객체와)
                                            // ->  영속성 컨텍스트에, 준영속 객체 값을 가지고 와 덮어씌움.
                                            //-> 하지만 transaction을 안걸었으니, 데이터베이스에 커밋은 안함.

        //캐싱된 영속 객체가 반환됨.
        Menu mergedMenu = entityManager.find(Menu.class, 2); //데이터베이스에서 찾아오는 것인데, 커밋이 안됬는데 어떻게 true??
        assertEquals("수박죽", mergedMenu.getMenuName());  //entity

    }

    @Test
    public void 병합_merge_삽입_테스트() {

        Menu menuToDetach = entityManager.find(Menu.class, 2); //영속.
        entityManager.detach(menuToDetach); //준영속.

        menuToDetach.setMenuCode(999); //데이터 변경. 코드 변경.(아이디/pk)
        menuToDetach.setMenuName("수박죽"); //데이터 변경. 이름

        entityManager.merge(menuToDetach); //merge는 있으면 병합하고, 없으면 새로 만든다. -> 준영속이었으니, 새로 객체를 만듦.

        //새로운 데이터를 조회.
        Menu mergedMenu = entityManager.find(Menu.class, 999);
        assertEquals("수박죽", mergedMenu.getMenuName());

    }



}
