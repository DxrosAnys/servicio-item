package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.Config;
import com.formacionbdi.springboot.app.item.models.Item;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> body = new HttpEntity<>(producto);
        ResponseEntity<Producto> response =  clientRest.exchange(stdUrl.getUrl() + "/save", HttpMethod.POST, body, Producto.class);
        return response.getBody();
    }

    @Override
    public Producto update(Long id, Producto producto) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id",id.toString());

        HttpEntity<Producto> body = new HttpEntity<>(producto);
       // ResponseEntity<Producto> response =  clientRest.exchange(stdUrl.getUrl() + "/edit/" + id, HttpMethod.PUT, body, Producto.class);
        ResponseEntity<Producto> response =  clientRest.exchange(stdUrl.getUrl() + "/edit/{id}", HttpMethod.PUT, body, Producto.class, pathVariables);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());

     // clientRest.delete(stdUrl.getUrl() + "/delete/" + id, HttpMethod.DELETE, Producto.class);
      clientRest.delete(stdUrl.getUrl() + "/delete/{id}", HttpMethod.DELETE, Producto.class, pathVariables);
    }
}
