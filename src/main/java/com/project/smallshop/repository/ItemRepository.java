package com.project.smallshop.repository;

import com.project.smallshop.domain.item.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepository {

    void save(Item item);

    Item findOne(Long itemId);

    List<Item> findOtherItems(Long itemId);

    List<Item> findAll();

    void remove(Long itemId);

    int findAllCnt();

    List<Item> findListPaging(int startIndex, int pageSize);
}
