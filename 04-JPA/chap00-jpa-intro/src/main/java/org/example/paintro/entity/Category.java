package org.example.paintro.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //접근제어자를 protected
@AllArgsConstructor
public class Category {

    @Id //pk라서 중복 안되게
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincreament그거라서,
    @Column(name = "category_code")  //안써도 되지만, 컬럼명이 일치해야함. 데이터베이스 columns . smal
    private Integer categorycode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_price")
    private Integer menuPrice;

    @Column(name = "category_code")
    private Integer categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;


    //참고만.
    public Category(String menuName, Integer menuPrice, Integer categoryCode, String orderableStatus) {
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
