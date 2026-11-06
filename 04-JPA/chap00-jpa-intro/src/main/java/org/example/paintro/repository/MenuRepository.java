package org.example.paintro.repository;

//통신을 수행하는 주체.
//D

import org.example.paintro.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//jpaRepository<엔디티 타입, 식별자 타입> //해당 타입에 맞는 menu. Integer는 id밖에 없으니.
//식별자라는 것은 @id 어노테이션을 붙인 것. 이것이 pk.
//그래서 식별자로 제약확인. menuCode 일치한 걸 조회하겠다는 등.
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query(value = "SELECT m From Menu m WHERE m.menuName = :menuName")  //여기서 네임쿼리처럼 쿼리 쓰고.
    public List<Menu> findMenuBycategoryName(@Param("menuName")String mennuName); //리스트로 추상메서드 만들기.

}
