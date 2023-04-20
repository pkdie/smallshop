package com.project.smallshop.domain.item;

import com.project.smallshop.domain.Review;
import com.project.smallshop.domain.CartItem;
import com.project.smallshop.domain.OrderItem;
import com.project.smallshop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType dtype;

    private String name;

    private int price;

    private int stock;

    private String image; //상품 이미지

    private String content; // 상품 설명

    private String outline;

    private String author;

    private String isbn;

    private String brand;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItems = new ArrayList<>();

    // ------------------- 비즈니스 로직 -------------------

    public void addStock(int quantity) {
        this.stock += quantity;
    }

    public int totalPrice(int count) {
        return this.getPrice() * count;
    }

    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stock = restStock;
    }

    public static Item createItem(ItemType dtype, String name, int price, int stock, String image, String content, String outline, String author, String isbn, String brand) {

        Item item = new Item();
        item.setDtype(dtype);
        item.setName(name);
        item.setPrice(price);
        item.setStock(stock);
        item.setImage(image);
        item.setContent(content);
        item.setOutline(outline);
        item.setAuthor(author);
        item.setIsbn(isbn);
        item.setBrand(brand);

        return item;
    }
}
