package org.example.paintro.dto;

import jakarta.persistence.Column;
import lombok.*;

//네트워크 통신할 때 받는 객체
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuRequestDTO {

   //안써도 되지만, 컬럼명이 일치해야함. 데이터베이스 columns . smal

    private String menuName;
    private Integer menuPrice;
    private Integer categoryCode;
    private String orderableStatus;

}
