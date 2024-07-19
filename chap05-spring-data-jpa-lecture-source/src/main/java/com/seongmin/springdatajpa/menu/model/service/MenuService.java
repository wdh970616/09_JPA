package com.seongmin.springdatajpa.menu.model.service;

import com.seongmin.springdatajpa.menu.model.dto.CategoryDto;
import com.seongmin.springdatajpa.menu.model.dto.MenuDto;
import com.seongmin.springdatajpa.menu.model.entity.Category;
import com.seongmin.springdatajpa.menu.model.entity.Menu;
import com.seongmin.springdatajpa.menu.model.repository.CategoryRepository;
import com.seongmin.springdatajpa.menu.model.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public MenuDto findMenuByCode(int menuCode) {

        // MenuDto -> 일반클래스(영속성 컨텍스트 관리X)
        // Menu -> Entity(영속성 컨텍스트 관리O)

        /*
         * findById 메소드는 이미 구현이 되어있다.
         * 반환값은 Optional 타입이고 Optional 타입은 NPE를 방지하기위해 다양한 기능을 제공한다.
         */
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        log.info("menu ========== {}", menu);

        return modelMapper.map(menu, MenuDto.class);
    }

    public List<MenuDto> findMenuList() {

        List<Menu> menuList = menuRepository.findAll(/* Sort.by("menuPrice").descending() -> menuPrice로 역순 정렬 */);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).collect(Collectors.toList());
    }

    public Page<MenuDto> findAllMenus(Pageable pageable) {

        // page 파라미터가 Pageable의 number로 넘어옴
        // 조회했을때는 인덱스 기준이 되기 때문에 getPageNumber에 -1을 해줌
        // PageRequest.of -> Pageable 객체 조작
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ?
                0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDto.class));
    }

    public List<MenuDto> findByMenuPrice(Integer menuPrice) {

        List<Menu> menuList = null;

        if (menuPrice == 0) {
            menuList = menuRepository.findAll();
        } else if (menuPrice > 0) {
            menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        }

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).collect(Collectors.toList());
    }

    public List<MenuDto> findMenuBySamePrice(Integer menuPrice) {

        List<Menu> menuList = null;

        menuList = menuRepository.findByMenuPriceEquals(menuPrice);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).collect(Collectors.toList());
    }

    public List<CategoryDto> findAllCategory() {

//        List<Category> categoryList = categoryRepository.findAll();
//        List<Category> categoryList = categoryRepository.findAllCategoryByJPQL();
        List<Category> categoryList = categoryRepository.findAllCategoryByNativeQuery();

        return categoryList.stream().map(category ->
                modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public void registNewMenu(MenuDto newMenu) {

//        Menu menu = modelMapper.map(newMenu, Menu.class);

        // Builder 적용
        Menu menu = new Menu().builder()
                .MenuName(newMenu.getMenuName())
                .menuPrice(newMenu.getMenuPrice())
                .categoryCode(newMenu.getCategoryCode())
                .orderableStatus(newMenu.getOrderableStatus())
                .build();

        menuRepository.save(menu);
    }

    @Transactional
    public void modifyMenu(MenuDto modifyMenu) {

        // modifyMenu -> 비영속
        // 영속

        Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        foundMenu.setMenuName(modifyMenu.getMenuName());
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {

        menuRepository.deleteById(menuCode);
    }
}
