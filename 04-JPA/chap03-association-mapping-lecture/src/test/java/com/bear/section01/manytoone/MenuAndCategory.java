package com.bear.section01.manytoone;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuAndCategory {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @JoinColumn(name = "category_code", nullable = true, updatable = false, insertable = true )
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;

}
