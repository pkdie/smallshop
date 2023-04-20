package com.project.smallshop.Service;

import com.project.smallshop.domain.item.Book;
import com.project.smallshop.domain.item.Clothes;
import com.project.smallshop.domain.item.Food;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.repository.ItemRepository;
import com.project.smallshop.repository.ItemRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceImplTest {

    @Autowired ItemService itemService;

    @Test
    @Rollback(value = false)
    public void 상품등록() {
        //given
        Book book = new Book();
        book.setIsbn("123");
        Food food = new Food();
        food.setName("고기");
        Clothes clothes = new Clothes();
        clothes.setBrand("나이키");

        //when

        itemService.save(book);
        itemService.save(food);
        itemService.save(clothes);

        //then
    }
}