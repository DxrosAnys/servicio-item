package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.item.models.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findALl();
    public Item findById(Long id, Integer cantidad);
}
