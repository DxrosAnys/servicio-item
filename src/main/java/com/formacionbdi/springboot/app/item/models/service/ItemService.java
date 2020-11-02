package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.commons.models.entity.Producto;

import java.util.List;

public interface ItemService {
    List<Item> findALl();
    Item findById(Long id, Integer cantidad);
    Producto save(Producto producto);
    Producto update(Long id, Producto producto);
    void delete(Long id);
}
