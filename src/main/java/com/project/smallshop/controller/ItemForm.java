package com.project.smallshop.controller;

import com.project.smallshop.domain.item.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {

    private Long id;

    private ItemType dtype;

    private String name;

    private int price;

    private int stock;

    private String outline;

    private String author;

    private String isbn;

    private String brand;
}
