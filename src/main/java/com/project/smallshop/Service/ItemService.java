package com.project.smallshop.Service;

import com.project.smallshop.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Long save(Item item);

    Item findOne(Long itemId);

    void updateItem(Long itemId, String name, int price, int stock, String author, String isbn, String brand);

    List<Item> findAll();

    List<Item> findOtherItems(Long itemId);

    void remove(Long itemId);

    int findAllCnt();

    List<Item> findListPaging(int startIndex, int pageSize);
}
