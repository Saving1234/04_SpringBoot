package org.example.paintro.controller;

import lombok.RequiredArgsConstructor;
import org.example.paintro.dto.MenuRequestDTO;
import org.example.paintro.dto.MenuResponseDTO;
import org.example.paintro.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Controller -> service 로 보냄 -> 여러 repository 계층으로.
 */

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor  // 초기화 할 필드가 필요한 경우에만.
public class MenuController {

    private final MenuService menuService; //final 붙었으니까 초기화 해야하니까.

    //전체조회
    @GetMapping("user")
    public List<MenuResponseDTO> finalAll(){

        return menuService.findALL();
    }


    @PostMapping
    public MenuResponseDTO create(@RequestBody MenuRequestDTO menuRequestDTO){

        return menuService.create(menuRequestDTO);

    }

    @PutMapping("/{menuCode}")
    public MenuResponseDTO update(@PathVariable Integer menuCode, @RequestBody MenuRequestDTO request){

        return menuService.update(menuCode,request);
    }


}
