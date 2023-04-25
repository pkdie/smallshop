package com.project.smallshop.controller;

import com.project.smallshop.Service.ItemService;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.dto.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "1") int page) {

        // 총 게시물 수
        int totalListCnt = itemService.findAllCnt();

        // 생성인자로 총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(totalListCnt, page);

        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        List<Item> items = itemService.findListPaging(startIndex, pageSize);

        model.addAttribute("items", items);
        model.addAttribute("pagination", pagination);

        return "home";
    }
}
