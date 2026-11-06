package com.bear.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManyToOneAssociationTests {

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
		클래스 간의 관계를 매핑하는 것.
		1. 다중성에 의한 분류
		N:1 (Manytonoe)
		1:N (OneTomany)
		1:1 (oneToOne)
		N:M (ManyToMany)
		2.방향에 따른 분류
		단방향.
		양방향.

	 */

	@Test
	public void 다대일_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {

		// given
		int menuCode = 15;

		// when
		// @ManyToOne의 기본 로딩 전략 EAGER.
		//Join은 보장되지 않음! (실제 sql은 jpa구현체, 조회방식의 차이에 따라 join또는 별도의 select로 가지고 올 수도 있음. 즉, 결과는 같다는 건가?)
		MenuAndCategory foundMenu = entityManager.find(MenuAndCategory.class, menuCode);
		System.out.println("조회전");
		Category menuCategory = foundMenu.getCategory();

		// then
		assertNotNull(menuCategory);
		System.out.println("foundMenu = " + foundMenu);
		System.out.println("menuCategory = " + menuCategory);
	}

	/*

	 */

	@Test
	public void 다대일_연관관계_객체지향쿼리_사용한_카테고리_이름_조회_테스트() {

		// given
						//엔티티명을 테이블 명으로 쓰고 있음. 컬럼명이 아니라, 객체의 필드를 컬럼으로 작성해도 됨.
		String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = 15";

		// when
		String category = entityManager.createQuery(jpql, String.class).getSingleResult();

		// then
		assertNotNull(category);
		System.out.println("category = " + category);
	}


	@Test
	public void 다대일_연관관계_객체_삽입_테스트() {

		// given

		//객체 만듦. 값을 넣기 위해 새 객체를 만듬. 메모리에 넣을 공간이 있어야해서.
		MenuAndCategory menuAndCategory = new MenuAndCategory(); //db에 없음.
		menuAndCategory.setMenuCode(99999);
		menuAndCategory.setMenuName("죽방멸치빙수");
		menuAndCategory.setMenuPrice(30000);

		//객체만듦. 이것도 같은 의미로 새 객체 만듦.
		Category category = new Category(); //db에 없음. 부모가 db에 없음?? 새로운 엔티티를 만들어서 새로운 카테고리를 만들고, db에 저장하려 했는데 안됐음.
		//-> 새 메뉴를 만들었는데, 새 카테고리는 영속성으로 만들지 않아서(cascade 없어서). 다만 cascade 설정을 persist 시, 영속화 ->
		category.setCategoryCode(33333);
		category.setCategoryName("신규카테고리");
		category.setRefCategoryCode(null);

		menuAndCategory.setCategory(category);
		menuAndCategory.setOrderableStatus("Y");

		// when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(menuAndCategory);

		entityTransaction.commit();

		/*
			persist()에 전달한 Entity는 menuAndCategory 하나뿐인데 category까지 함께 저장됨.
			-> MenuCategory의 ManyToOne에 CascadeType.persist로 영속성 전파를 해놨기에 -> 영속성 컨텍스트에 들어감.
		 */

		// then
		MenuAndCategory foundMenuAndCategory = entityManager.find(MenuAndCategory.class, 99999);

		assertEquals(99999, foundMenuAndCategory.getMenuCode());
		assertEquals(33333, foundMenuAndCategory.getCategory().getCategoryCode());

	}

}







