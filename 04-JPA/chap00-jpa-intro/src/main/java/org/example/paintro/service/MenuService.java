package org.example.paintro.service;

//비즈니즈 로직을 짜는 곳.
//회원가입, 로그인 같은 것들.

import lombok.RequiredArgsConstructor;
import org.example.paintro.dto.MenuRequestDTO;
import org.example.paintro.dto.MenuResponseDTO;
import org.example.paintro.entity.Menu;
import org.example.paintro.repository.MenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//컴포넌트.
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuResponseDTO> findALL() {

        List<Menu> menuList = menuRepository.findAll(); //데이터베이스에서 조회 가동.

        List<MenuResponseDTO> list = menuList.stream()
                .map(menu -> MenuResponseDTO.from(menu))
                .toList();

        return list;

    }

    //저장하려고.
    //이 메서드 안에서 일어나는 작업들을 하나의 작업으로 묶겠다??
    //
    @Transactional
    public MenuResponseDTO create(MenuRequestDTO request) {

        Menu menu = new Menu(request.getMenuName(), request.getMenuPrice(), request.getCategoryCode(), request.getOrderableStatus());

        Menu savedMenu = menuRepository.save(menu);

        return MenuResponseDTO.from(savedMenu);
    }

    //영속성 컨텍스트 : JPA를 이용해서 만드는 entity.
    //entity는 영속 상태. -> 변경이 발생하면 DB에 반영. JDBC와 동기화.
    //언제 바꿔줄거냐. 트렌젝션을 이용해서 바꿔줄 시간을 정해주나?
    //트렌젝션이 끝나면 영속상태의 객체 변화를 DataBase에 반영.
    @Transactional
    public MenuResponseDTO update(Integer menuCode, MenuRequestDTO request) {

        // id 로 수정할 메뉴 정보 찾아오기
        Menu menu = menuRepository.findById(menuCode) //메뉴코드가 일치하면 변경해라.
                .orElseThrow(//메뉴코드가 일치하지 않으면.
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."));
        // 값 변경(일치하면)
        menu.change(
                request.getMenuName(),
                request.getMenuPrice(),
                request.getCategoryCode(),
                request.getOrderableStatus()
        );

        return MenuResponseDTO.from(menu);
    }
}

