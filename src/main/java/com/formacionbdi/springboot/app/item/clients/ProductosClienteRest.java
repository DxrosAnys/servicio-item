package com.formacionbdi.springboot.app.item.clients;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductosClienteRest {

    @GetMapping("/list")
    List<Producto> listar();

    @GetMapping("/list/{id}")
    Producto detalle(@PathVariable Long id);

    @PostMapping("/save")
    Producto create(@RequestBody Producto producto);

    @PutMapping("/edit/{id}")
    Producto update(@PathVariable("id") Long id, @RequestBody Producto producto);

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
