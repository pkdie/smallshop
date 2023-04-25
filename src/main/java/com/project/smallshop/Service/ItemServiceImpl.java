package com.project.smallshop.Service;

import com.project.smallshop.domain.item.Item;
import com.project.smallshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    @Override
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    @Override
    public void updateItem(Long itemId, String name, int price, int stock, String author, String isbn, String brand) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStock(stock);
        item.setAuthor(author);
        item.setIsbn(isbn);
        item.setBrand(brand);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findOtherItems(Long itemId) {
        return itemRepository.findOtherItems(itemId);
    }

    @Transactional
    @Override
    public void remove(Long itemId) {
        itemRepository.remove(itemId);
    }

    @Override
    public int findAllCnt() {
        return itemRepository.findAllCnt();
    }

    @Override
    public List<Item> findListPaging(int startIndex, int pageSize) {
        return itemRepository.findListPaging(startIndex, pageSize);
    }
}
