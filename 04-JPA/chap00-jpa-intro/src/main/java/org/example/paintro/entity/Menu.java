package org.example.paintro.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//쿼리 수행 결과 저장하는 클래스.
//DB 컬럼.
//영속상태라 데이터베이스와 연결을 하는건가???
//영속성 컨텍스트와 엔티티와의 관계를 찾아보고 정리해라.
//영속성 컨텍스트가 뭔지?
//엔티티가 뭔지?
@Entity
@Table(name = "tbl_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //접근제어자를 protected
@AllArgsConstructor
public class Menu {

    @Id //pk라서 중복 안되게
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincreament그거라서,
    @Column(name = "menu_code")  //안써도 되지만, 컬럼명이 일치해야함. 데이터베이스 columns . smal
    private Integer menucode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private Integer menuPrice;

    @Column(name = "category_code")
    private Integer categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;


    //참고만.
    public Menu(String menuName, Integer menuPrice, Integer categoryCode, String orderableStatus) {
        change(menuName, menuPrice, categoryCode, orderableStatus);
    }
//생성자에서 호출한 메서드.참고만.
    public void change(String menuName, Integer menuPrice, Integer categoryCode, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

}
