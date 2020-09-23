package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.item.clients.ProductosClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @Primary
@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductosClienteRest clienteFeign;

    @Override
    public List<Item> findALl() {
        return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(clienteFeign.detalle(id), cantidad);
    }
}
