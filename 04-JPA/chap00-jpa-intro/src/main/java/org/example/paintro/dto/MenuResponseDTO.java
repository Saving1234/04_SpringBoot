package org.example.paintro.dto;

//네트워크 통신할 때 보내는 객체

import lombok.*;
import org.example.paintro.entity.Menu;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuResponseDTO {
    private Integer menuCode;
    private String menuName;
    private Integer menuPrice;
    private Integer categoryCode;
    private String orderableStatus;

    // Menu 클래스를 dto로 변환.
    //http 통신하기위한 dto.
    public static MenuResponseDTO from(Menu menu){
        return new MenuResponseDTO(
                menu.getMenucode(),
                menu.getMenuName(),
                menu.getMenuPrice(),
                menu.getCategoryCode(),
                menu.getOrderableStatus()
        );
    }


}
