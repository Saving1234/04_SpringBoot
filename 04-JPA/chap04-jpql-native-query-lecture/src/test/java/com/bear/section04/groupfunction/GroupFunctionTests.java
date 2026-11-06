package com.bear.section04.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupFunctionTests {
	
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
		JPQL의 그룹함수 COUNT, MAX, MIN, SUM, AVG 등의 그룹함수도 사용할 수 있다.
		주의사항!!!
		1. 그룹함수의 반환타입은 결과값이 정수면 Long, 실수면 Double
		2. 값이 없는 상태에서 그룹함수는 null 응답.(count만 0을 응답) -> 기본자료형을 사용하면 NullpointException 발생. (래핑클래스로 사용하는게 좋다.)
	 */
	
	@Test
	public void 특정_카테고리의_등록된_메뉴_수_조회() {
		
		// given
		int categoryCodeParameter = 4;
		
		// when
		//Count는 0으로도 나올 수도 있다. null일 시.
		String jpql = "SELECT COUNT(m.menuPrice) FROM menu_section04 m WHERE m.categoryCode = :categoryCode";
		long countOfMenu = entityManager.createQuery(jpql, Long.class)
				.setParameter("categoryCode", categoryCodeParameter)
				.getSingleResult();
		
		// then
		assertTrue(countOfMenu >= 0);
		System.out.println("countOfMenu = " + countOfMenu);
		
	}

	//assertThrows : 원하는 예외를 발생시키는지 확인하는 테스트를 할 때 사요하는 검증.
	// assertThrows(원하는 클래스, () -> {예외발생코드})
	
	@Test
	public void count를_제외한_다른_그룹함수의_조회결과가_없는_경우_테스트() {
		
		// given
		int categoryCodeParameter = 3;

		// when
		String jpql = "SELECT SUM(m.menuPrice) FROM menu_section04 m WHERE m.categoryCode = :categoryCode";

		// then
		assertThrows(NullPointerException.class, () -> { //기본자료형
			long sumOfPrice = entityManager.createQuery(jpql, Long.class)
					.setParameter("categoryCode", categoryCodeParameter)
					.getSingleResult();

		});

		assertDoesNotThrow(() -> {  //래핑클래스. null 값을 담을 수 있기 때문에 null 허용.
			Long sumOfPrice = entityManager.createQuery(jpql, Long.class)
					.setParameter("categoryCode", categoryCodeParameter)
					.getSingleResult();

		});
	}
	
	@Test
	public void group_by절과_having절을_사용한_조회_테스트() {
		
		//given
		long minPrice = 50000L; //결과값이 정수니까 ,long을 씀.
		
		//when
		String jpql = "SELECT m.categoryCode, SUM(m.menuPrice) " +
						"FROM menu_section04 m " +
						"GROUP BY m.categoryCode " +
						"HAVING SUM(m.menuPrice) >= :minPrice";
		
		List<Object[]> sumPriceOfCategoryList = entityManager.createQuery(jpql, Object[].class)
				.setParameter("minPrice", minPrice)
				.getResultList();
		
		//then
		assertNotNull(sumPriceOfCategoryList);
		sumPriceOfCategoryList.forEach(row -> {
			Arrays.stream(row).forEach(System.out::println);
		});
	}
}
