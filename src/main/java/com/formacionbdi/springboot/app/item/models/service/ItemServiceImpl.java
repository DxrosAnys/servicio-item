package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.item.AppConfig;
import com.formacionbdi.springboot.app.item.Config;
import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service("serviceTemplate")
public class ItemServiceImpl implements ItemService{

    private final RestTemplate clientRest;

    private final Config stdUrl;

    public ItemServiceImpl(RestTemplate clientRest, Config stdUrl) {
        this.clientRest = clientRest;
        this.stdUrl = stdUrl;
    }

    @Override
    public List<Item> findALl() {
        List<Producto> productos = Arrays.asList(Objects.requireNonNull(clientRest.getForObject( stdUrl.getUrl() + "/list", Producto[].class)));
        return productos.stream().map( p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Producto producto = clientRest.getForObject(stdUrl.getUrl()  + "/list/{id}", Producto.class, pathVariables);
        return new Item(producto, cantidad);
    }
}
