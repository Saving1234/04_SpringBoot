package com.bear.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OneToManyAssociationTests {
	
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
	public void 일대다_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
		
		// given
		int categoryCode = 10;
		
		// when
		System.out.println("find() 호출하기 일보 직전!");
		CategoryAndMenu categoryAndMenu = entityManager.find(CategoryAndMenu.class, categoryCode);
		System.out.println("find() 호출 완료");
		//category코드만 가져온 다음에, menu를 가져옴. 한번에 다 가져오는게 아니라.
		// 디버깅 할 때는, System.out.println(categoryAndMenu); 이거 호출하기 위해 menu도 가져와버림.

		// then
		assertNotNull(categoryAndMenu);

		System.out.println("categoryAndMenu 인스턴스에 최초로 접근하기 일보 직전!");
		System.out.println(categoryAndMenu);
		System.out.println("categoryAndMenu 인스턴스에 최초로 접근 완료.");
	}
	
	@Test
	public void 일대다_연관관계_객체_삽입_테스트() {
		
		// given
		CategoryAndMenu categoryAndMenu = new CategoryAndMenu();

		categoryAndMenu.setCategoryCode(888);
		categoryAndMenu.setCategoryName("일대다추가카테고리");
		categoryAndMenu.setRefCategoryCode(null);
		
		List<Menu> menuList = new ArrayList<>();
		Menu menu = new Menu();

		menu.setMenuCode(777);
		menu.setMenuName("일대다아이스크림");
		menu.setMenuPrice(50000);
		menu.setOrderableStatus("Y");

		//새로운 menu를 만들 때 부모 키가 있어야한다.
		//테이블 관계에서는 외래키는 N쪽인 menu가 들고 있는데.
		//객체 관계어세느느 categoryandmenu가 외래키를 관리하고 있기 때문에
		//menu를 insert하 ㄴ 뒤에 연관관계에 외래키를 반영하기 위해 update 추가될 수 있다.
		//--> fk로 cateory가 menu의 pk를 가져오는데,
		//category가
		menu.setCategoryCode(categoryAndMenu.getCategoryCode());
		
		menuList.add(menu);
		
		categoryAndMenu.setMenuList(menuList);
		
		// when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(categoryAndMenu);

		System.out.println("commit() 호출 직전");
		entityTransaction.commit();
		System.out.println("commit() 호출 완료");

		CategoryAndMenu foundCategoryAndMenu = entityManager.find(CategoryAndMenu.class, 888);

		System.out.println(foundCategoryAndMenu);
	}

}
